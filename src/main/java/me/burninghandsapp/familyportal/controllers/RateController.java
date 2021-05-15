package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.models.BlogPostRatings;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostRatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RateController extends  BaseController {

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    private BlogPostRatingsRepository blogPostRatingsRepository;


    @GetMapping("/RateItem/{id}")
    public String RateItem(@PathVariable(value="id") Integer id, Model model)
    {
        getbaseModel(model,"pages/ratepost :: main",-1);



        //get the item or new rateobject
        var rateCount =  blogPostRatingsRepository.findCountByRateByAndBlog(id,loginUser.getId());
        BlogPostRatings rateobj = new BlogPostRatings();
        if(rateCount == 0)
        {

            rateobj.setBlogItem(blogPostItemsRepository.getOne(id));
            rateobj.setRateBy(loginUser);
            rateobj.setRate(0);
        }
        else
        {
            rateobj = blogPostRatingsRepository.findByBlog(id, loginUser.getId());
        }
        model.addAttribute("rateobject",rateobj);

        return  "Default";
    }


    @PostMapping("CreateOrSaveRatting")
    public RedirectView DeleteCategory(@ModelAttribute BlogPostRatings blogPostRatings, Model model)
    {
        blogPostRatingsRepository.saveAndFlush(blogPostRatings);


        var avgRate=  blogPostRatingsRepository.findAvgByBlog(blogPostRatings.getBlogItem().getId());

        var blogItem = blogPostItemsRepository.findOneById(blogPostRatings.getBlogItem().getId());

        blogItem.setAvgRate(avgRate);

        blogPostItemsRepository.saveAndFlush(blogItem);

        return new RedirectView("/blog/post/"+blogPostRatings.getBlogItem().getId());
    }



}
