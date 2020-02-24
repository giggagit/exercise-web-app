package com.giggagit.exam.Service;

import java.util.Map;

import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Model.UserModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * UserService
 */
public interface UserService extends UserDetailsService{

    public void save(UserModel userModel);
    public Boolean create(UserModel userModel);
    public void update(UserModel userModel);
    public void updateContext(Authentication authentication, UserModel userModel);
    public void deleteById(int userId);
    public Boolean exist(int userId);
    public UserModel findByid(int userId);
    public UserModel findByUsername(String username);
    public Page<UserModel> findAll(Pageable pageable);
    public Boolean changePassword(Authentication authentication, UserModel userModel);
    public ScoreModel submitExam(int topicId, String username, Map<String, String> userAnswer, TopicModel topicModel);
    
}