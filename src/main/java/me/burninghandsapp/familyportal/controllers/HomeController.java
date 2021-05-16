package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.repositories.BlogPostItemCommentsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostRatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends  BaseController {

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    private BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    @Autowired
    private BlogPostRatingsRepository blogPostRatingsRepository;

    @GetMapping("/")
    public String Index( Model model) {
        getbaseModel(model,"pages/index :: main",1);

         getLoginUser();

        model.addAttribute("articlescount",blogPostItemsRepository.findCountByAuthor(loginUser.getId()));

        model.addAttribute("commentsCount",blogPostItemCommentsRepository.findCountByAuthor(loginUser.getId()));

        model.addAttribute("rankingcount",blogPostRatingsRepository.findCountByRateBy(loginUser.getId()));

        model.addAttribute("myrecentarticles",blogPostItemsRepository.myRecentArticles(loginUser.getId()));

        model.addAttribute("articlestoreview",blogPostItemsRepository.articleToReview(loginUser.getId()));

        model.addAttribute("latestreviews",blogPostItemsRepository.myLatestReviewReceived(loginUser.getId()));



        return "Default";
    }




}
