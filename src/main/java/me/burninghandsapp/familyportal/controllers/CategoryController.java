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
        getbaseModel(model,"pages/categories :: main",3);
        return "Default";
    }

    @GetMapping("/Category")
    public String Category(@RequestParam Integer id, Model model)
    {
        getbaseModel(model,"pages/category :: main",2);
        model.addAttribute("category",categoryRepository.getOne(id));
        model.addAttribute("a2",id);
        return "Default";
    }
}
