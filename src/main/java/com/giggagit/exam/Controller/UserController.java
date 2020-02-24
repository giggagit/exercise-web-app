package com.giggagit.exam.Controller;

import com.giggagit.exam.GroupValidation.Profile;
import com.giggagit.exam.Model.UserModel;
import com.giggagit.exam.Service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * UserController
 */
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("pageNav")
    public String pageNav() {
        return "profile";
    }

    // Login page
    @GetMapping("/login")
    public String login(Model model) {
        return "user/login";
    }

    // Register page
    @GetMapping("/register")
    public String userRegister(UserModel userModel, Model model) {
        return "user/register";
    }

    // Create new user
    @PostMapping("/register")
    public String registerProcess(@Validated({Profile.class}) UserModel userModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.create(userModel)) {
            return "redirect:/register?success";
        } else {
            return "redirect:/register?error";
        }
    }

}