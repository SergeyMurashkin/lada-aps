package ru.murashkin.ladaAps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.murashkin.ladaAps.models.Customer;
import ru.murashkin.ladaAps.repositories.CustomerRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/info")
    public String customer(Model model,
                           @AuthenticationPrincipal Customer customer) {
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Страница пользователя");

        return "customers/customer";
    }

    @PatchMapping("/edit")
    public String updateCustomer(@ModelAttribute("firstName") @Valid String firstName,
                                 BindingResult bindingResult1,
                                 @ModelAttribute("lastName") @Valid String lastName,
                                 BindingResult bindingResult2,
                                 @AuthenticationPrincipal Customer customer) {

        if (bindingResult1.hasErrors()) {
            return "customers/edit";
        }

        if (bindingResult2.hasErrors()) {
            return "customers/edit";
        }

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        customerRepository.save(customer);

        return "redirect:/customers/info";
    }

    @GetMapping("/edit")
    public String getCustomerForUpdate(Model model,
                                       @AuthenticationPrincipal Customer customer) {
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Страница редактирования профиля");

        return "customers/edit";
    }


}
