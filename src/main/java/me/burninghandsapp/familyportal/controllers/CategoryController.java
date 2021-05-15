package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.models.Categories;
import me.burninghandsapp.familyportal.repositories.BlogPostItemCommentsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostRatingsRepository;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;



@Controller
public class CategoryController extends  BaseController {

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    private BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    @Autowired
    private BlogPostRatingsRepository blogPostRatingsRepository;

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


    @PostMapping("/DeleteCategory")
    public  RedirectView DeleteCategory(Integer Id, Model model)
    {
        categoriesRepository.deleteById(Id);
        return new RedirectView("/Categories");
    }

}
