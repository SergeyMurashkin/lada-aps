package ru.murashkin.ladaAps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.murashkin.ladaAps.models.Customer;
import ru.murashkin.ladaAps.repositories.CustomerRepository;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/info")
    public String customer(Model model,
                           Authentication authentication) {
        Customer customer = null;

        try {
            customer = customerRepository.findByUsername(authentication.getName());
            model.addAttribute("customer", customer);
        } catch (NoSuchElementException ex) {
            model.addAttribute("error", "Страница пользователя не найдена");
        }

        model.addAttribute("title", "Страница пользователя");

        return "customers/customer";
    }

    @PatchMapping("/edit")
    public String updateCustomer(@ModelAttribute("firstName") @Valid String firstName,
                                 BindingResult bindingResult1,
                                 @ModelAttribute("lastName") @Valid String lastName,
                                 BindingResult bindingResult2,
                                 Authentication authentication) {
        if (bindingResult1.hasErrors()) {
            return "customers/edit";
        }

        if (bindingResult2.hasErrors()) {
            return "customers/edit";
        }

        Customer customerForUpdate = customerRepository.findByUsername(authentication.getName());

        customerForUpdate.setFirstName(firstName);
        customerForUpdate.setLastName(lastName);

        customerRepository.save(customerForUpdate);

        return "redirect:/customers/info";
    }

    @GetMapping("/edit")
    public String getCustomerForUpdate(Model model,
                                       Authentication authentication) {

        Customer customer = null;

        try {
            customer = customerRepository.findByUsername(authentication.getName());
            model.addAttribute("customer", customer);
        } catch (NoSuchElementException ex) {
            model.addAttribute("error", "Страница пользователя не найдена");
        }

        model.addAttribute("title", "Страница редактирования профиля");

        return "customers/edit";
    }


}
