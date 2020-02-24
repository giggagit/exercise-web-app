package com.giggagit.exam.Controller;

import com.giggagit.exam.Form.ExamForm;
import com.giggagit.exam.GroupValidation.Profile;
import com.giggagit.exam.Model.AnnounceModel;
import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Model.UserModel;
import com.giggagit.exam.Service.AnnounceService;
import com.giggagit.exam.Service.ScoreService;
import com.giggagit.exam.Service.TopicService;
import com.giggagit.exam.Service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AdminController
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TopicService topicService;
    private final UserService userService;
    private final AnnounceService announceService;
    private final ScoreService scoreService;

    public AdminController(TopicService topicService, UserService userService,
            AnnounceService announceService, ScoreService scoreService) {
        this.topicService = topicService;
        this.userService = userService;
        this.announceService = announceService;
        this.scoreService = scoreService;
    }

    @ModelAttribute("pageNav")
    public String pageNav() {
        return "admin";
    }

    // Admin Page
    @RequestMapping({ "", "/" })
    public String adminPage(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
            Model model) {
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<TopicModel> pageModel = topicService.findAll(pageable);

        model.addAttribute("pageModel", pageModel);
        model.addAttribute("pageLink", "admin");
        model.addAttribute("adminMenu", "index");
        
        return "admin/index";
    }

    // New Topic
    @GetMapping("/topic/new")
    public String topicCreate(TopicModel topicModel, Model model) {
        model.addAttribute("adminMenu", "index");
        return "admin/topic/new";
    }

    // Perform new Topic
    @PostMapping("/topic/new")
    public String topicCreateProcess(@Validated TopicModel topicModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/topic/new";
        }

        topicModel.setStatus(false);
        topicService.newTopic(topicModel);
        return "redirect:/admin?new=success";
    }

    // Delete topic
    @GetMapping("topic/delete/{topicId}")
    public String topicDelete(@PathVariable("topicId") int topicId) {
        if (topicService.exist(topicId)) {
            topicService.deleteById(topicId);
            return "redirect:/admin?delete=success";
        }

        return "redirect:/admin?delete=error";
    }

    // Edit topic
    @GetMapping("/topic/{topicId}")
    public String topicEdit(@PathVariable("topicId") int topicId, Model model) {
        TopicModel topicModel = topicService.findById(topicId);

        model.addAttribute("topicModel", topicModel);
        model.addAttribute("adminMenu", "index");
        model.addAttribute("topicId", topicId);
        model.addAttribute("topicTab", "index");

        return "admin/topic/index";
    }

    // Perform edit topic
    @PostMapping("/topic/{topicId}")
    public String topicEditProcess(@PathVariable("topicId") int topicId, TopicModel topicModel,
            Model model) {
        topicService.save(topicModel);
        return "redirect:/admin/topic/" + topicId + "?success";
    }
    
    // Edit exam
    @GetMapping("/topic/{topicId}/exam")
    public String examEdit(@PathVariable("topicId") int topicId, Model model) {
        String pageTitle = null;
        ExamForm examForm = null;
        TopicModel topicModel = topicService.findById(topicId);

        if (topicModel != null) {
            pageTitle = topicModel.getTitle();
            examForm = new ExamForm();
            examForm.setExamModel(topicModel.getExamModel());
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("examForm", examForm);
        model.addAttribute("topicId", topicId);
        model.addAttribute("adminMenu", "index");
        model.addAttribute("topicTab", "exam");

        return "admin/topic/exam";
    }

    // Perform edit exam
    @PostMapping("/topic/{topicId}/exam")
    public String examEditProcess(@PathVariable("topicId") int topicId, ExamForm examForm) {
        topicService.editExam(topicId, examForm);
        return "redirect:/admin/topic/" + topicId + "/exam?success";
    }

    // Exam user
    @GetMapping("/topic/{topicId}/users")
    public String topicUsers(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @PathVariable("topicId") int topicId, Model model) {
        String pageTitle = null;
        Pageable pageable = PageRequest.of(page-1, 10);        
        Page<ScoreModel> pageModel = scoreService.findByTopicId(topicId, pageable);
        TopicModel topicModel = topicService.findById(topicId);

        if (topicModel != null) {
            pageTitle = topicModel.getTitle();
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("topicId", topicId);
        model.addAttribute("pageLink", "admin/topic/" + topicId + "/users");
        model.addAttribute("adminMenu", "index");
        model.addAttribute("topicTab", "users");

        return "admin/topic/users";
    }

    // Delete user
    @GetMapping("/user-management")
    public String userManagement(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
            Model model) {
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<UserModel> pageModel = userService.findAll(pageable);

        model.addAttribute("pageModel", pageModel);
        model.addAttribute("adminMenu", "user");

        return "admin/user-management/index";
    }

    // New user page
    @GetMapping("/user-management/new")
    public String userNew(UserModel userModel, Model model) {
        model.addAttribute("adminMenu", "user");
        return "admin/user-management/new";
    }

    // Perform new user
    @PostMapping("/user-management/new")
    public String userNewPerform(@Validated({Profile.class}) UserModel userModel, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/user-management/new";
        }

        if (userService.create(userModel)) {
            return "redirect:/admin/user-management?new=success";
        } else {
            return "redirect:/admin/user-management?new=error";
        }
    }

    // Perform delete user
    @GetMapping("/user-management/delete/{userId}")
    public String userDelete(@PathVariable("userId") int userId, Model model) {
        if (userService.exist(userId)) {
            userService.deleteById(userId);
            return "redirect:/admin/user-management?delete=success";
        }

        return "redirect:/admin/user-management?delete=error";
    }

    // User detail
    @GetMapping("/user-management/detail/{userId}")
    public String userDetail(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("userModel", userService.findByid(userId));
        model.addAttribute("adminMenu", "user");
        return "admin/user-management/detail";
    }

    // Update user detail
    @PostMapping("/user-management/detail/{userId}")
    public String userDetailProcess(@Validated(Profile.class) UserModel userModel, 
            BindingResult bindingResult, @PathVariable("userId") int userId, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("adminMenu", "user");
            return "admin/user-management/detail";
        }

        userService.save(userModel);
        return "redirect:/admin/user-management/detail?id=" + userId;
    }

    // Announce Page
    @GetMapping("/announce")
    public String announce(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
            AnnounceModel announceModel, Model model) {
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<AnnounceModel> pageModel = announceService.findAll(pageable);

        model.addAttribute("pageLink", "admin/announce");
        model.addAttribute("pageModel", pageModel);
        model.addAttribute("adminMenu", "announce");

        return "admin/announce/index";
    }

    // New announce
    @PostMapping("/announce")
    public String announceCreateProcess(@Validated AnnounceModel announceModel,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("adminMenu", "announce");
            return "admin/announce";
        }

        announceService.save(announceModel);
        return "redirect:/admin/announce?new=success";
    }

    // Delete announce
    @GetMapping("/announce/delete/{announceId}")
    public String announceDelete(@PathVariable("announceId") int announceId) {
        if (announceService.exist(announceId)) {
            announceService.deleteById(announceId);
            return "redirect:/admin/announce?delete=success";
        }

        return "redirect:/admin/announce?delete=error";
    }

}