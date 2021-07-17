package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.modeldto.BlogPostItemsDto;
import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.models.BlogPostItems;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
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

    private final BlogPostItemsRepository blogPostItemsRepository;

    private final CategoriesRepository categoriesRepository;

    public static final  String BLOG_POST_DETAILS_PAGE = "pages/blogpostdetails :: main";

    public static final  String NEW_POST_PAGE = "pages/newpost :: main";

    public  static final String EDIT_POST_PAGE = "pages/editpost :: main";


    @Autowired
    public BlogController(CategoriesRepository categoryRepository, UserRepository userRepository, BlogPostItemsRepository blogPostItemsRepository, CategoriesRepository categoriesRepository) {
        super(categoryRepository, userRepository);
        this.loginUser = new UserDto();
        this.blogPostItemsRepository = blogPostItemsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping("/details/article/{id}")
    public String getBlogPostDetails(@PathVariable(value="id") Integer id, Model model)
    {
        getBaseModel(model,BLOG_POST_DETAILS_PAGE,-1);

        var blogpost = blogPostItemsRepository.findOneById(id);

        model.addAttribute("article", blogpost);

        return DEFAULT_PAGE;
    }

    @GetMapping("/new/article")
    public String getNewPost(Model model)
    {
        getBaseModel(model,NEW_POST_PAGE,-1);

        model.addAttribute("newpost",new BlogPostItems());

        return DEFAULT_PAGE;
    }

    @GetMapping("/edit/article/{id}")
    public String getEditBlog(@PathVariable(value="id") Integer id, Model model)
    {
        getBaseModel(model,EDIT_POST_PAGE,-1);
        var blogpost = blogPostItemsRepository.findOneById(id);

        var editBlogDto = new BlogPostItemsDto();
        mapper.map(blogpost,editBlogDto);

        model.addAttribute("newpost",editBlogDto);
        var category = blogpost.getCategory();
        category.setHasArticles(true);
        categoriesRepository.saveAndFlush(category);

        return DEFAULT_PAGE;
    }

    @PostMapping("/save/new/article")
    public  RedirectView newPost(@ModelAttribute BlogPostItemsDto blogPostItems , Model model)
    {
        getLoginUser();
        blogPostItems.setAuthor(loginUser);
        var blogItem = new BlogPostItems();


        mapper.map(blogPostItems,blogItem);



        blogItem.setDateCreated(getNow());
        blogItem.setAvgRate(0);
        blogPostItemsRepository.saveAndFlush(blogItem);
        var id = String.valueOf( blogItem.getId());

        var category = blogItem.getCategory();
        category.setHasArticles(true);
        categoriesRepository.saveAndFlush(category);

        return new RedirectView("/details/article/"+id);
    }

    @PostMapping("/save/article")
    public RedirectView editPost(@ModelAttribute BlogPostItemsDto blogPostItems , Model model)
    {
        getLoginUser();
        blogPostItems.setAuthor(loginUser);

        var blogPost = new BlogPostItems();
        mapper.map(blogPostItems,blogPost);

        blogPostItemsRepository.saveAndFlush(blogPost);
        var id = String.valueOf( blogPost.getId());
        return new RedirectView("/details/article/"+id);
    }
}
