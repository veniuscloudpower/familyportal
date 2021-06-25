package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends  BaseController {

    private final BlogPostItemsRepository blogPostItemsRepository;

    private final BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    private final BlogPostRatingsRepository blogPostRatingsRepository;

    public static final String HOME_PAGE ="pages/index :: main";

    @Autowired
    public HomeController(CategoriesRepository categoryRepository, UserRepository userRepository, BlogPostItemsRepository blogPostItemsRepository, BlogPostItemCommentsRepository blogPostItemCommentsRepository, BlogPostRatingsRepository blogPostRatingsRepository) {
        super(categoryRepository, userRepository);
        this.blogPostItemsRepository = blogPostItemsRepository;
        this.blogPostItemCommentsRepository = blogPostItemCommentsRepository;
        this.blogPostRatingsRepository = blogPostRatingsRepository;
    }

    @GetMapping("/")
    public String getHomeAction( Model model) {
        getBaseModel(model,HOME_PAGE,1);

         getLoginUser();

        model.addAttribute("articlescount",blogPostItemsRepository.findCountByAuthor(loginUser.getId()));

        model.addAttribute("commentsCount",blogPostItemCommentsRepository.findCountByAuthor(loginUser.getId()));

        model.addAttribute("rankingcount",blogPostRatingsRepository.findCountByRateBy(loginUser.getId()));

        model.addAttribute("myrecentarticles",blogPostItemsRepository.myRecentArticles(loginUser.getId()));

        model.addAttribute("articlestoreview",blogPostItemsRepository.articleToReview(loginUser.getId()));

        model.addAttribute("latestreviews",blogPostItemsRepository.myLatestReviewReceived(loginUser.getId()));



        return DEFAULT_PAGE;
    }




}
