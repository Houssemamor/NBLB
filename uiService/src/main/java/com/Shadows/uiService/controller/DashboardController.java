package com.Shadows.uiService.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    @GetMapping("/uiService/dashboard")
    public String dashboard(@RequestParam(value = "token", required = false) String token,
                           HttpSession session, Model model) {
        if (token != null) {
            // Store token in session for API calls
            session.setAttribute("jwtToken", token);
            model.addAttribute("successMessage", "Login successful!");
        }
        return "dashboard";
    }
}