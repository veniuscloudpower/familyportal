package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController extends BaseController {

    public  static  final String SETTINGS_PAGE = "pages/settings :: main";

    @Autowired
    public SettingsController(CategoriesRepository categoryRepository, UserRepository userRepository) {
        super(categoryRepository, userRepository);
        this.loginUser = new UserDto();
    }

    @GetMapping("/Settings")
    public String getSettings( Model model) {
        getBaseModel(model,SETTINGS_PAGE,5);
        return DEFAULT_PAGE;
    }

}
