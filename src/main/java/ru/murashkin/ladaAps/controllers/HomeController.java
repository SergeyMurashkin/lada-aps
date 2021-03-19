package ru.murashkin.ladaAps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.murashkin.ladaAps.models.Customer;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        return "about";
    }

    @GetMapping("/store")
    public String store(Model model) {
        model.addAttribute("title", "Магазин");
        return "store";
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("title", "Страница регистрации");

        model.addAttribute("customer", new Customer());
        return "signUp";
    }

}