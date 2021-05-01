package me.burninghandsapp.familyportal.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends  BaseController {



    @GetMapping("/")
    public String Index( Model model) {
        getbaseModel(model,"pages/index :: main");
        return "Default";
    }




}
