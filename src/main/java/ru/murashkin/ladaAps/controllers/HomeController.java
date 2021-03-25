package ru.murashkin.ladaAps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.murashkin.ladaAps.models.Customer;
import ru.murashkin.ladaAps.models.Role;
import ru.murashkin.ladaAps.repositories.CustomerRepository;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }

        Customer customerWithEmail = customerRepository.findByEmail(customer.getEmail());
        if (customerWithEmail != null) {
            model.addAttribute("emailDuplicateError", "Email уже существует");
            return "signUp";
        }

        Customer customerWithUsername = customerRepository.findByUsername(customer.getUsername());
        if (customerWithUsername != null) {
            model.addAttribute("usernameDuplicateError", "Username уже существует");
            return "signUp";
        }

        String passwordBeforeCrypt = customer.getPassword();

        customer.setActive(true);
        customer.setRoles(Collections.singleton(Role.USER));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("emailDuplicateError", ex.getMessage());
            customer.setPassword(passwordBeforeCrypt);
            return "signUp";
        } catch (Throwable ex) {
            customer.setPassword(passwordBeforeCrypt);
            return "signUp";
        }

        return "redirect:/login";
    }

}