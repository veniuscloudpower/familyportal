package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.modelview.UserView;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BaseController {

    @Autowired
     CategoriesRepository categoryRepository;

    @Autowired
    UserRepository userRepository;


    Model getbaseModel(Model model,String pagesection,int activemenu )
    {
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("pagefragment",pagesection);
        model.addAttribute("pagefragmentScript",pagesection.replace("main","script"));
        model.addAttribute("pagefragmentStyle",pagesection.replace("main","style"));
        model.addAttribute("m1", getActiveMenu("m1",activemenu));
        model.addAttribute("m2", getActiveMenu("m2",activemenu));
        model.addAttribute("m3", getActiveMenu("m3",activemenu));
        model.addAttribute("m4", getActiveMenu("m4",activemenu));
        model.addAttribute("m5", getActiveMenu("m5",activemenu));

       var loginuser = userRepository.getUserByUsername(getUserName());

       var userview = new UserView(loginuser.getUsername(),loginuser.getAvatar(),loginuser.getFirstName(),loginuser.getLastName());

        model.addAttribute("loggedUserDetails",userview);
        return model;
    }

    private  String getUserName()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            return  username;
        } else {
            String username = principal.toString();
            return  username;
        }
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
