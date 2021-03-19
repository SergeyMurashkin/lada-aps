package ru.murashkin.ladaAps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.ls.LSOutput;
import ru.murashkin.ladaAps.models.Customer;
import ru.murashkin.ladaAps.repositories.CustomerRepository;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public String customer(@PathVariable("id") Long id,
                           Model model) {
        Customer customer = null;

        try {
            customer = customerRepository.findById(id).get();
            model.addAttribute("customer", customer);
        } catch (NoSuchElementException ex) {
            model.addAttribute("error", "Страница пользователя не найдена");
        }

        model.addAttribute("title", "Страница пользователя");

        return "customers/customer";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()) {
            return "signUp";
        }

        Customer newCustomer = null;
        try {
            newCustomer = customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("emailDuplicateError", "Email с таким именем уже существует");
            return "signUp";
        }

        return "redirect:/customers/" + newCustomer.getId();
    }

    @PatchMapping("edit/{id}")
    public String updateCustomer(@ModelAttribute("firstName") @Valid String firstName,
                                 BindingResult bindingResult1,
                                 @ModelAttribute("lastName") @Valid String lastName,
                                 BindingResult bindingResult2,
                                 @PathVariable("id") Long id) {
        if(bindingResult1.hasErrors()) {
            return "customers/edit";
        }

        if(bindingResult2.hasErrors()) {
            return "customers/edit";
        }

        Customer customerForUpdate = customerRepository.findById(id).get();

        customerForUpdate.setFirstName(firstName);
        customerForUpdate.setLastName(lastName);

        customerRepository.save(customerForUpdate);

        return "redirect:/customers/" + id;
    }

    @GetMapping("edit/{id}")
    public String getCustomerForUpdate(@PathVariable("id") Long id,
                                       Model model) {

        Customer customer = null;

        try {
            customer = customerRepository.findById(id).get();
            model.addAttribute("customer", customer);
        } catch (NoSuchElementException ex) {
            model.addAttribute("error", "Страница пользователя не найдена");
        }

        model.addAttribute("title", "Страница редактирования профиля");

        return "customers/edit";
    }



}
