package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BaseController {

    @Autowired
     CategoriesRepository categoryRepository;


    Model getbaseModel(Model model,String pagesection )
    {
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("pagefragment",pagesection);
        return model;
    }

}
