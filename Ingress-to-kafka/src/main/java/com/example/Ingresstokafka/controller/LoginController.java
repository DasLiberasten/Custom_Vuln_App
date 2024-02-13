package com.example.Ingresstokafka.controller;

import com.example.Ingresstokafka.ClickHouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public InMemoryUserDetailsManager userDetailsManager;

    @PostMapping("/authenticate")
    //public String authenticate(@RequestParam("username") String username, 
    public String authenticate(@RequestParam("username") String username, 
                               @RequestParam("password") String password, 
                               Model model) {
        boolean isValid = ClickHouse.main(false, username, password);
        if (isValid) {
            if (!userDetailsManager.userExists(username)) {
                userDetailsManager.createUser(User.withUsername(username).password(password).roles("USER").build());
            }
            logger.info("User authenticated: {}", username);
            // Пользователь аутентифицирован, перенаправляем на другую страницу
            return "redirect:/index"; // Например, страница успешной аутентификации
        } else {
            // Пользователь не аутентифицирован, показываем ошибку
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Остаемся на странице входа
        }
    }


    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    
}
