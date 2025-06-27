package com.example.ITCompany.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверный email или пароль");
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