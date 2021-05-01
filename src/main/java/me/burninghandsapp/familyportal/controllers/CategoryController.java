package me.burninghandsapp.familyportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CategoryController extends  BaseController {

    @GetMapping("/Categories")
    public  String Categories(Model model)
    {
        getbaseModel(model,"pages/categories :: main");
        return "Default";
    }

    @GetMapping("/Category")
    public String Category(@RequestParam Integer id, Model model)
    {
        getbaseModel(model,"pages/category :: main");
        model.addAttribute("category",categoryRepository.getOne(id));
        return "Default";
    }
}
