package me.burninghandsapp.familyportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController extends BaseController {

    @GetMapping("/Users")
    public String Index( Model model) {
        getbaseModel(model,"pages/users :: main",4);

        model.addAttribute("users",userRepository.findAll());
        return "Default";
    }
}
