package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.models.BlogPostItems;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class CategoryController extends  BaseController {

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @GetMapping("/Categories")
    public  String Categories(Model model)
    {
        getbaseModel(model,"pages/categories :: main",3);
        return "Default";
    }

    @GetMapping("/Category")
    public String Category(@RequestParam Integer id, @RequestParam(defaultValue = "1") Integer page, Model model)
    {
        getbaseModel(model,"pages/category :: main",2);
        var category = categoryRepository.getOne(id);
        model.addAttribute("category",category);

        model.addAttribute("a2",id);

         model.addAttribute("articles", blogPostItemsRepository.findAllByCategory(id,10,(10*(page-1))));

        return "Default";
    }

    @GetMapping("/NewPost")
    public String NewPost(Model model)
    {
        getbaseModel(model,"pages/newpost :: main",-1);

        model.addAttribute("newpost",new BlogPostItems());

        return "Default";
    }


    @PostMapping("/NewPost")
    public  String NewPost(@ModelAttribute BlogPostItems blogPostItems , Model model)
    {
        blogPostItemsRepository.saveAndFlush(blogPostItems);

        return "Default";
    }

}
