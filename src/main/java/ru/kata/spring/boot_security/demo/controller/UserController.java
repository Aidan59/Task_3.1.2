package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring_mvc.enums.EmploymentType;
import spring_mvc.model.User;
import spring_mvc.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String users(Model model) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("employmentTypes", EmploymentType.values());

        return "users";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (checkBindingErrors(user, result, model)) {
            return "users";
        }
        userService.addUser(user);

        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {
        User user = new User();
        user.setId(id);

        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.getUser(user));
        model.addAttribute("employmentTypes", EmploymentType.values());

        return "users";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (checkBindingErrors(user, result, model)) {
            return "users";
        }

        userService.updateUser(user);

        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        User user = new User();
        user.setId(id);

        userService.deleteUser(user);

        return "redirect:/users";
    }

    private boolean checkBindingErrors(
            @ModelAttribute("user") @Valid User user,
            BindingResult result, Model model) {
        if (!result.hasFieldErrors("email") && userService.existsByEmail(user.getEmail(), user.getId())) {
            result.rejectValue("email", "duplicate", "This email is already in use");
        }
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("employmentTypes", EmploymentType.values());
            return true;
        }
        return false;
    }
}
