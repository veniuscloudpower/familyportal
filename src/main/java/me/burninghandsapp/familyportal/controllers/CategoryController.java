package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.models.Categories;
import me.burninghandsapp.familyportal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;



@Controller
public class CategoryController extends  BaseController {

    private final BlogPostItemsRepository blogPostItemsRepository;

    public static final  String CATEGORY_LIST_PAGE = "pages/categories :: main";

    public static final String CATEGORY_PAGE = "pages/category :: main";

    public static  final  String CATEGORIES_LIST_URL = "/categories";


    @Autowired
    public CategoryController(CategoriesRepository categoryRepository, UserRepository userRepository, BlogPostItemsRepository blogPostItemsRepository) {
        super(categoryRepository, userRepository);
        this.loginUser = new UserDto();
        this.blogPostItemsRepository = blogPostItemsRepository;
    }

    @GetMapping("/categories")
    public  String getCategoriesList(Model model)
    {

        getBaseModel(model,CATEGORY_LIST_PAGE,3);
        return DEFAULT_PAGE;
    }

    @GetMapping("/list/category/{id}")
    public String getCategory(@PathVariable(value="id") Integer id, @RequestParam(defaultValue = "1") Integer page, Model model)
    {

        getBaseModel(model,CATEGORY_PAGE,2);
        var category = categoryRepository.getOne(id);
        model.addAttribute("category",category);

        model.addAttribute("a2",id);

        model.addAttribute("nextpage",page+1);

        model.addAttribute("prevpage",page-1);

        Pageable pageRequestOf12 = PageRequest.of(page-1, 12);

         model.addAttribute("articles", blogPostItemsRepository.findAllByCategory(id, pageRequestOf12));

        return DEFAULT_PAGE;
    }



    @PostMapping("/new/category")
    public RedirectView newCategory(String categoryName,String categoryDescription)
    {
        var category = new Categories();
        category.setCategoryName(categoryName);
        category.setCategoryDescription(categoryDescription);
        category.setHasArticles(false);
        categoryRepository.saveAndFlush(category);

        return new RedirectView(CATEGORIES_LIST_URL);
    }

    @PostMapping("/edit/category")
    public RedirectView editCategory(String categoryName,String categoryDescription,Integer id)
    {
        var objectCategory = categoryRepository.findById(id);
        if(objectCategory.isPresent())
        {
            var category = objectCategory.get();
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryDescription);
            categoryRepository.saveAndFlush(category);
        }


        return new RedirectView(CATEGORIES_LIST_URL);
    }


    @PostMapping("/delete/category")
    public  RedirectView deleteCategory(Integer id)
    {
        categoryRepository.deleteById(id);
        return new RedirectView(CATEGORIES_LIST_URL);
    }

}
