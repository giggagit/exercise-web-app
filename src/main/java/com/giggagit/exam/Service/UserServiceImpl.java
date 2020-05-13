package com.giggagit.exam.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.giggagit.exam.Model.CustomUserDetails;
import com.giggagit.exam.Model.ExamModel;
import com.giggagit.exam.Model.RoleModel;
import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Model.UserModel;
import com.giggagit.exam.Repository.RoleRepository;
import com.giggagit.exam.Repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    private final ScoreService scoreService;
    private final TopicService topicService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(ScoreService scoreService, TopicService topicService, UserRepository userRepository,
            RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.scoreService = scoreService;
        this.topicService = topicService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (RoleModel role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails userDetails = new CustomUserDetails(user);
        return (userDetails);
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public Boolean create(UserModel userModel) {
        Boolean registerStatus = false;

        if (userModel.getPassword().equals(userModel.getPasswordConfirm())) {

            if (userRepository.findByUsername(userModel.getUsername()) == null) {
                userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
                userModel.setRoles(new HashSet<RoleModel>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
                save(userModel);
                registerStatus = true;
            }

        }

        return (registerStatus);
    }

    @Override
    public void deleteById(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Boolean exist(int userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void update(UserModel userModel) {
        UserModel updateUser = findByUsername(userModel.getUsername());

        // Update user detail
        updateUser.setFirstname(userModel.getFirstname());
        updateUser.setLastname(userModel.getLastname());
        updateUser.setEmail(userModel.getEmail());
        updateUser.setClassroom(userModel.getClassroom());

        save(updateUser);
    }

    @Override
    public void updateContext(Authentication authentication, UserModel userModel) {
        UserModel updateUser = findByUsername(authentication.getName());

        // Update user detail
        updateUser.setFirstname(userModel.getFirstname());
        updateUser.setLastname(userModel.getLastname());
        updateUser.setEmail(userModel.getEmail());
        updateUser.setClassroom(userModel.getClassroom());

        // Update user's SecurityContextHolder.Details
        UserDetails userDetails = new CustomUserDetails(updateUser);
        UsernamePasswordAuthenticationToken contextDetails = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(contextDetails);

        save(updateUser);
    }

    @Override
    public ScoreModel submitExam(int topicId, String username, Map<String, String> userAnswer, TopicModel topicModel) {
        int userScore = 0;
        ScoreModel scoreModel = new ScoreModel();

        // Convert Map<String,String> to Map<Integer,Integer>
        Map<Integer, Integer> userAnswers = userAnswer.entrySet().stream().collect(Collectors
                .toMap(entry -> Integer.parseInt(entry.getKey()), entry -> Integer.parseInt(entry.getValue())));
        TopicModel getTopic = topicService.findById(topicId);
        UserModel userModel = findByUsername(username);

        for (ExamModel examAnswer : getTopic.getExamModel()) {
            if (examAnswer.getAnswer() == userAnswers.get(examAnswer.getId())) {
                userScore++;
            }
        }

        scoreModel = scoreService.result(userScore, getTopic.getPassScore(), getTopic, userModel);

        return scoreModel;
    }

    @Override
    public UserModel findByid(int userId) {
        return userRepository.findById(userId).orElseThrow(null);
    }

    @Override
    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Boolean changePassword(Authentication authentication, UserModel userModel) {
        Boolean changeStatus = false;
        CustomUserDetails authenUser = (CustomUserDetails) authentication.getPrincipal();
        UserModel updateUser = findByUsername(authentication.getName());

        // Compare password with database
        if (bCryptPasswordEncoder.matches(userModel.getPassword(), authenUser.getPassword())) {
            // Compare new password and confirm password
            if (userModel.getPasswordNew().equals(userModel.getPasswordConfirm())) {
                // Set new password
                updateUser.setPassword(bCryptPasswordEncoder.encode(userModel.getPasswordNew()));

                // Update user's SecurityContextHolder.Details
                UserDetails userDetails = new CustomUserDetails(updateUser);
                UsernamePasswordAuthenticationToken contextDetails = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(contextDetails);

                save(updateUser);
                changeStatus = true;
            }
        }

        return changeStatus;
    }

}