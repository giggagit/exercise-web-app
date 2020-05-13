package com.giggagit.exam.Controller;

import com.giggagit.exam.GroupValidation.Password;
import com.giggagit.exam.GroupValidation.Profile;
import com.giggagit.exam.Model.UserModel;
import com.giggagit.exam.Service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ProfileController
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("pageNav")
    public String pageNav() {
        return "profile";
    }

    // Profile page
    @GetMapping({ "", "/" })
    public String profile(Authentication authentication, Model model) {

        model.addAttribute("userModel", userService.findByUsername(authentication.getName()));
        model.addAttribute("userMenu", "index");

        return "user/profile/index";
    }

    // Update profile page
    @GetMapping("/update-profile")
    public String updateProfile(Authentication authentication, Model model) {

        model.addAttribute("userModel", userService.findByUsername(authentication.getName()));
        model.addAttribute("userMenu", "update");

        return "user/profile/update-profile";
    }

    // Perform update profile
    @PostMapping("/update-profile")
    public String updateProfileProcess(@Validated(Profile.class) UserModel userModel, BindingResult bindingResult,
            Authentication authentication, Model model) {
        model.addAttribute("userMenu", "update");

        if (bindingResult.hasErrors()) {
            return "user/profile/update-profile";
        }

        userService.updateContext(authentication, userModel);
        return "redirect:/profile/update-profile?success";
    }

    // Change password page
    @GetMapping("/change-password")
    public String changePassword(UserModel userModel, Model model) {
        model.addAttribute("userMenu", "password");
        return "user/profile/change-password";
    }

    // Perform change password
    @PostMapping("/change-password")
    public String changePasswordProcess(@Validated(Password.class) UserModel userModel, BindingResult bindingResult,
            Authentication authentication, Model model) {
        model.addAttribute("userMenu", "password");

        if (bindingResult.hasErrors()) {
            return "user/profile/change-password";
        }

        if (userService.changePassword(authentication, userModel)) {
            return "redirect:/profile/change-password?success";
        }

        return "redirect:/profile/change-password?error";
    }

}