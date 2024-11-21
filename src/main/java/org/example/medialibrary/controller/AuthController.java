package org.example.medialibrary.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private AppUserService userService;

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registerUser(AppUser user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/registration";
        }

        userService.registerUser(user);
        return "redirect:/user";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}

