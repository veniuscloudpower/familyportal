package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.models.BlogPostItemComments;
import me.burninghandsapp.familyportal.models.BlogPostItems;
import me.burninghandsapp.familyportal.models.Categories;
import me.burninghandsapp.familyportal.repositories.BlogPostItemCommentsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class CategoryController extends  BaseController {

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    private BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

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

        model.addAttribute("nextpage",page+1);

        model.addAttribute("prevpage",page-1);

         model.addAttribute("articles", blogPostItemsRepository.findAllByCategory(id,12,(12*(page-1))));



        return "Default";
    }

    @GetMapping("/blog/post/{id}")
    public String BlogPostDetails(@PathVariable(value="id") Integer id, Model model)
    {
        getbaseModel(model,"pages/blogpostdetails :: main",-1);

        var blogpost = blogPostItemsRepository.findOneById(id);




        model.addAttribute("article", blogpost);


        return "Default";
    }

    @PostMapping("/newcategory")
    public RedirectView newcategory(String CategoryName,String CategoryDescription,Model model)
    {
        Categories category = new Categories();
        category.setCategoryName(CategoryName);
        category.setCategoryDescription(CategoryDescription);
        category.setHasArticles(false);
        categoriesRepository.saveAndFlush(category);

        return new RedirectView("/Categories");
    }

    @PostMapping("/editcategory")
    public RedirectView editcategory(String CategoryName,String CategoryDescription,Integer Id,Model model)
    {
        Categories category = categoryRepository.findById(Id).get();
        category.setCategoryName(CategoryName);
        category.setCategoryDescription(CategoryDescription);
        categoriesRepository.saveAndFlush(category);

        return new RedirectView("/Categories");
    }

    @PostMapping("/CommentsAdd")
    public  RedirectView CommentAdd(String commenttext,int blogId ,Model model)
    {

        BlogPostItemComments comments = new BlogPostItemComments();
        comments.setCommentText(commenttext);
        comments.setAuthor(userRepository.getUserByUsername(getUserName()));



        comments.setDateCreated(getNow());
        var blog = blogPostItemsRepository.findById(blogId);

        if (blog.isPresent())
        {
            comments.setBlogItem(blog.get());
        }


        blogPostItemCommentsRepository.saveAndFlush(comments);

        var id = String.valueOf( blogId);
        return new RedirectView("/blog/post/"+id);
    }

    @GetMapping("/NewPost")
    public String NewPost(Model model)
    {
        getbaseModel(model,"pages/newpost :: main",-1);

        model.addAttribute("newpost",new BlogPostItems());

        return "Default";
    }


    @GetMapping("/EditPost/{id}")
    public String EditBlog(@PathVariable(value="id") Integer id, Model model)
    {
        getbaseModel(model,"pages/editpost :: main",-1);
        var blogpost = blogPostItemsRepository.findOneById(id);
        model.addAttribute("newpost",blogpost);
        var category = blogpost.getCategory();
        category.setHasArticles(true);
        categoriesRepository.saveAndFlush(category);

        return "Default";
    }

    @PostMapping("/NewPost")
    public  RedirectView NewPost(@ModelAttribute BlogPostItems blogPostItems , Model model)
    {
        blogPostItems.setAuthor(userRepository.getUserByUsername(getUserName()));
        blogPostItems.setDateCreated(getNow());
        blogPostItems.setAvgRate(0);
        blogPostItemsRepository.saveAndFlush(blogPostItems);
        var id = String.valueOf( blogPostItems.getId());

        var category = blogPostItems.getCategory();
        category.setHasArticles(true);
        categoriesRepository.saveAndFlush(category);

        return new RedirectView("/blog/post/"+id);
    }

    @PostMapping("/EditPost")
    public RedirectView EditPost(@ModelAttribute BlogPostItems blogPostItems , Model model)
    {
        blogPostItemsRepository.saveAndFlush(blogPostItems);
        var id = String.valueOf( blogPostItems.getId());
        return new RedirectView("/blog/post/"+id);
    }

}
