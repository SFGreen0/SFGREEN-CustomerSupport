package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        if ("admin".equals(username) && "password".equals(password)) {
            return "redirect:/tickets";
        } else {
            return "loginForm";
        }
    }
}