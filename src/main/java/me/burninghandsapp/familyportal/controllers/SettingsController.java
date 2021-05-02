package me.burninghandsapp.familyportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController extends BaseController {

    @GetMapping("/Settings")
    public String Index( Model model) {
        getbaseModel(model,"pages/settings :: main",5);
        return "Default";
    }

}
