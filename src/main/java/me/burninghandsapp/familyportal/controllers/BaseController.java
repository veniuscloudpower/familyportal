package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BaseController {

    @Autowired
     CategoriesRepository categoryRepository;


    Model getbaseModel(Model model,String pagesection,int activemenu )
    {
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("pagefragment",pagesection);


        model.addAttribute("m1", getActiveMenu("m1",activemenu));
        model.addAttribute("m2", getActiveMenu("m2",activemenu));
        model.addAttribute("m3", getActiveMenu("m3",activemenu));
        model.addAttribute("m4", getActiveMenu("m4",activemenu));
        model.addAttribute("m5", getActiveMenu("m5",activemenu));

        return model;
    }

    private String getActiveMenu(String menuID,int activemenu)
    {
        var menuindex = menuID.substring(1);
       if (Integer.parseInt(menuindex) == activemenu)
       {
           if (activemenu!=2) {
               return "active";
           }
           else
           {
               return  "menu-open";
           }


       }
        return  "";
    }

}