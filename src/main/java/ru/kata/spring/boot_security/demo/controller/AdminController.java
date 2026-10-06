package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.enums.EmploymentType;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String VIEW = "index";
    private static final String REDIRECT = "redirect:/admin";

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String users(Model model) {
        fillPage(model);
        model.addAttribute("user", new User());

        return VIEW;
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            Model model) {

        user.setRoles(userService.findRolesByIds(roleIds));

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.rejectValue("password", "blank", "Password is required");
        }
        if (hasErrors(user, result, model)) {
            return VIEW;
        }

        userService.addUser(user);

        return REDIRECT;
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {
        User user = new User();
        user.setId(id);

        User found = userService.getUser(user);
        if (found == null) {
            return REDIRECT;
        }

        fillPage(model);
        model.addAttribute("user", found);

        return VIEW;
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            Model model) {

        user.setRoles(userService.findRolesByIds(roleIds));

        if (hasErrors(user, result, model)) {
            return VIEW;
        }

        userService.updateUser(user);

        return REDIRECT;
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        User user = new User();
        user.setId(id);

        userService.deleteUser(user);

        return REDIRECT;
    }

    private boolean hasErrors(User user, BindingResult result, Model model) {
        if (!result.hasFieldErrors("email")
                && userService.existsByEmail(user.getEmail(), user.getId())) {
            result.rejectValue("email", "duplicate", "This email is already in use");
        }
        if (result.hasErrors()) {
            fillPage(model);
            return true;
        }
        return false;
    }

    private void fillPage(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("employmentTypes", EmploymentType.values());
        model.addAttribute("allRoles", userService.findAllRoles());
    }
}