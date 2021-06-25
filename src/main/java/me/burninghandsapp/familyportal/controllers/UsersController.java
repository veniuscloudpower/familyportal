package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.WebSecurityConfig;
import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.models.User;
import me.burninghandsapp.familyportal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;
import java.util.Base64;

@Controller
public class UsersController extends BaseController {



    final
    BlogPostItemsRepository blogPostItemsRepository;

    final
    BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    final
    BlogPostRatingsRepository blogPostRatingsRepository;

    final
    WebSecurityConfig webSecurityConfig;

    public  static final String USERS_PAGE_LIST_URL ="/users";

    @Autowired
    public UsersController(CategoriesRepository categoryRepository, UserRepository userRepository, BlogPostItemsRepository blogPostItemsRepository, BlogPostItemCommentsRepository blogPostItemCommentsRepository, BlogPostRatingsRepository blogPostRatingsRepository, WebSecurityConfig webSecurityConfig) {
        super(categoryRepository, userRepository);
        this.blogPostItemsRepository = blogPostItemsRepository;
        this.blogPostItemCommentsRepository = blogPostItemCommentsRepository;
        this.blogPostRatingsRepository = blogPostRatingsRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    @GetMapping("/users")
    public String getUserList( Model model) {
        getBaseModel(model,"pages/users :: main",4);

        model.addAttribute("users",userRepository.findAll());
        return DEFAULT_PAGE;
    }

    @PostMapping("/new/user")
    public  RedirectView newUser(UserDto user,Model model)
    {

        var encodedPassword = webSecurityConfig.passwordEncoder().encode(user.getPassword());
        var newUser = new User();
        mapper.map(user,newUser);
        newUser.setEnabled(true);
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);
        return new RedirectView(USERS_PAGE_LIST_URL);
    }

    @PostMapping(value = "/users/change/avatar")
    public  RedirectView changeUserAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        getLoginUser();

        var fileBytes = file.getBytes();

        var fileBase64 = Base64.getMimeEncoder().encodeToString(fileBytes);

        var avatarFile = "data:image/png;base64,"+fileBase64;

        loginUser.setAvatar(avatarFile);

        var userToUpdate = new User();

        mapper.map(loginUser,userToUpdate);

        userRepository.save(userToUpdate);
        return new RedirectView("/");
    }

    @PostMapping("/delete/user")
    public  RedirectView deleteUser(Long id , Model model)
    {
        var haschildobjects = false;
        var blogitemcount = blogPostItemsRepository.findCountByAuthor(id);
        if (blogitemcount >0)
        {
            haschildobjects = true;
        }
        if (!haschildobjects) {
            var blogitemcommentscount = blogPostItemCommentsRepository.findCountByAuthor(id);
            if (blogitemcommentscount>0)
            {
                haschildobjects = true;
            }
        }

        if(!haschildobjects) {
            var blogItemRatingCount = blogPostRatingsRepository.findCountByRateBy(id);
            if (blogItemRatingCount>0)
            {
                haschildobjects = true;
            }
        }

        if (haschildobjects){
            var userRetrieved = userRepository.findById(id);
            if(userRetrieved.isPresent())
            {
                var userToDeactivate =userRetrieved.get();
                userToDeactivate.setEnabled(false);
                userRepository.save(userToDeactivate);
            }

        }
        else
        {
            userRepository.deleteById(id);
        }

        return  new RedirectView(USERS_PAGE_LIST_URL);
    }

    @PostMapping("/reset/user/password")
    public  RedirectView resetUserPassword(Long id ,String password , Model model)
    {

        var editUser = userRepository.findById(id);

        if (editUser.isPresent()) {
            var encodedPassword = webSecurityConfig.passwordEncoder().encode(password);
            var editUserValue = editUser.get();
            editUserValue.setPassword(encodedPassword);

            userRepository.save(editUserValue);
        }

        return  new RedirectView(USERS_PAGE_LIST_URL);
    }

    @PostMapping("/edit/user")
    public RedirectView editUser(UserDto user, Model model)
    {

        var userObject = userRepository.findById(user.getId());

        if (userObject.isPresent())
        {
            var editUser = userObject.get();
            editUser.setFirstName(user.getFirstName());
            editUser.setLastName(user.getLastName());
            editUser.setEmail(user.getEmail());
            editUser.setMobile(user.getMobile());
            editUser.setRole(user.getRole());
            editUser.setEnabled(user.isEnabled());

            userRepository.save(editUser);
        }


        return new RedirectView(USERS_PAGE_LIST_URL);
    }

}
