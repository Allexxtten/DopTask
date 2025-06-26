package com.example.ITCompany.controller;
import com.example.ITCompany.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "email", required = false) String email, Model model) {
        if (error != null) {
            boolean userExists = authService.userExists(email);
            if (!userExists) {
                model.addAttribute("errorMessage", "Пользователь с таким email не найден");
            } else {
                model.addAttribute("errorMessage", "Неверный пароль");
            }
        }
        return "login";
    }

    @GetMapping("/welcome")
    public String welcomePage(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("email", email);
        return "welcome";
    }
}