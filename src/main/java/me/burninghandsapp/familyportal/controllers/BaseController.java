package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.modelview.UserView;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class BaseController {

    final
    CategoriesRepository categoryRepository;

    final
    UserRepository userRepository;


    ModelMapper mapper;

    UserDto loginUser;

    public static final  String DEFAULT_PAGE = "default";

    @Autowired
    public BaseController(CategoriesRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
        this.loginUser = getLoginUser();
    }


    UserDto getLoginUser()
    {
        if (loginUser!=null)
        {
            if(loginUser.getId()!=null) {
                return loginUser;
            }
        }
        else
        {
            loginUser = new UserDto();
        }
        var loginUserName = getUserName();

        if (!loginUserName.isBlank()) {
            var loginUserEntity = userRepository.getUserByUsername(loginUserName);
            mapper.map(loginUserEntity, loginUser);
        }
          return loginUser;
    }

    Model getBaseModel(Model model,String pageSection,int activeMenu)
    {
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("pagefragment",pageSection);
        model.addAttribute("pagefragmentScript",pageSection.replace("main","script"));
        model.addAttribute("pagefragmentStyle",pageSection.replace("main","style"));
        model.addAttribute("m1", getActiveMenu("m1",activeMenu));
        model.addAttribute("m2", getActiveMenu("m2",activeMenu));
        model.addAttribute("m3", getActiveMenu("m3",activeMenu));
        model.addAttribute("m4", getActiveMenu("m4",activeMenu));
        model.addAttribute("m5", getActiveMenu("m5",activeMenu));

       var loginUserRetrieved = getLoginUser();

       var userView = new UserView();

        mapper.map(loginUserRetrieved,userView);
        model.addAttribute("loggedUserDetails",userView);
        return model;
    }

    public String getUserName()
    {
        try{
            var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal == null)
            {
                return  "";
            }
            if (principal instanceof UserDetails) {
                return  ((UserDetails)principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        catch (Exception ex)
        {
            return "";
        }

    }

    public LocalDateTime getNow()
    {
        var dateCreated = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return  LocalDateTime.parse(dateCreated,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String getActiveMenu(String menuID,int activeMenu)
    {
        var menuIndex = menuID.substring(1);
       if (Integer.parseInt(menuIndex) == activeMenu)
       {
           if (activeMenu!=2) {
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
