package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.models.BlogPostItems;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BlogController extends  BaseController {

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @GetMapping("/blog/post/{id}")
    public String BlogPostDetails(@PathVariable(value="id") Integer id, Model model)
    {
        getbaseModel(model,"pages/blogpostdetails :: main",-1);

        var blogpost = blogPostItemsRepository.findOneById(id);

        model.addAttribute("article", blogpost);

        return "Default";
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
        blogPostItems.setAuthor(loginUser);
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
