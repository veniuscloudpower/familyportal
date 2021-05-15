package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.WebSecurityConfig;
import me.burninghandsapp.familyportal.models.User;
import me.burninghandsapp.familyportal.repositories.BlogPostItemCommentsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostRatingsRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UsersController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    @Autowired
    BlogPostRatingsRepository blogPostRatingsRepository;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @GetMapping("/Users")
    public String Index( Model model) {
        getbaseModel(model,"pages/users :: main",4);

        model.addAttribute("users",userRepository.findAll());
        return "Default";
    }

    @PostMapping("/newuser")
    public  RedirectView newUser(User user,Model model)
    {
        user.setEnabled(true);
        userRepository.save(user);
        return new RedirectView("/Users");
    }

    @PostMapping("/DeleteUser")
    public  RedirectView deleteUser(Long Id , Model model)
    {
        var haschildobjects = false;
        var blogitemcount = blogPostItemsRepository.findCountByAuthor(Id);
        if (blogitemcount >0)
        {
            haschildobjects = true;
        }
        if (!haschildobjects) {
            var blogitemcommentscount = blogPostItemCommentsRepository.findCountByAuthor(Id);
            if (blogitemcommentscount>0)
            {
                haschildobjects = true;
            }
        }
        if(!haschildobjects) {
            var blogitemratingcount = blogPostRatingsRepository.findCountByRateBy(Id);
            if (blogitemratingcount>0)
            {
                haschildobjects = true;
            }
        }

        if (haschildobjects){
           var usertodeactivate = userRepository.findById(Id).get();
           usertodeactivate.setEnabled(false);
           userRepository.save(usertodeactivate);
        }
        else
        {
            userRepository.deleteById(Id);
        }

        return  new RedirectView("/Users");
    }

    @PostMapping("/resetuserpassword")
    public  RedirectView resetuserpassword(Long Id ,String password , Model model)
    {
        var edituser = userRepository.findById(Id).get();

        var encodedpassword = webSecurityConfig.passwordEncoder().encode(password);

        edituser.setPassword(encodedpassword);

        userRepository.save(edituser);

        return  new RedirectView("/Users");
    }

    @PostMapping("/edituser")
    public RedirectView edituser(User user, Model model)
    {
        var edituser = userRepository.findById(user.getId()).get();

        edituser.setFirstName(user.getFirstName());
        edituser.setLastName(user.getLastName());
        edituser.setEmail(user.getEmail());
        edituser.setMobile(user.getMobile());
        edituser.setRole(user.getRole());
        edituser.setEnabled(user.isEnabled());

        userRepository.save(edituser);
        return new RedirectView("/Users");
    }

}
