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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

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

    @GetMapping("/users")
    public String Index( Model model) {
        getbaseModel(model,"pages/users :: main",4);

        model.addAttribute("users",userRepository.findAll());
        return "Default";
    }

    @PostMapping("/new/user")
    public  RedirectView newUser(User user,Model model)
    {
        var encodedpassword = webSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedpassword);
        user.setEnabled(true);
        userRepository.save(user);
        return new RedirectView("/users");
    }

    @PostMapping(value = "/users/change/avatar")
    public  RedirectView changeavatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        getLoginUser();

        byte[] fileBytes = file.getBytes();

        var fileBase64 = Base64.getMimeEncoder().encodeToString(fileBytes);

        var avatarfile = "data:image/png;base64,"+fileBase64;



        loginUser.setAvatar(avatarfile);
        userRepository.save(loginUser);
        return new RedirectView("/");
    }

    @PostMapping("/delete/user")
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

        return  new RedirectView("/users");
    }

    @PostMapping("/reset/user/password")
    public  RedirectView resetuserpassword(Long Id ,String password , Model model)
    {
        var edituser = userRepository.findById(Id).get();

        var encodedpassword = webSecurityConfig.passwordEncoder().encode(password);

        edituser.setPassword(encodedpassword);

        userRepository.save(edituser);

        return  new RedirectView("/users");
    }

    @PostMapping("/edit/user")
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
        return new RedirectView("/users");
    }

}
