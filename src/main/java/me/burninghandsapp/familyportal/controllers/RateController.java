package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.modeldto.BlogPostItemsDto;
import me.burninghandsapp.familyportal.modeldto.BlogPostRatingsDto;
import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.models.BlogPostRatings;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostRatingsRepository;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.modelmapper.ModelMapper;
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

    private final BlogPostItemsRepository blogPostItemsRepository;

    private final BlogPostRatingsRepository blogPostRatingsRepository;

    @Autowired
    public RateController(CategoriesRepository categoryRepository, UserRepository userRepository, BlogPostItemsRepository blogPostItemsRepository, BlogPostRatingsRepository blogPostRatingsRepository) {
        super(categoryRepository, userRepository);
        this.loginUser = new UserDto();
        this.blogPostItemsRepository = blogPostItemsRepository;
        this.blogPostRatingsRepository = blogPostRatingsRepository;
    }


    @GetMapping("/rate/article/{id}")
    public String getRateItem(@PathVariable(value="id") Integer id, Model model)
    {
        getBaseModel(model,"pages/ratepost :: main",-1);

        getLoginUser();

        //get the item or new rateobject
        var rateCount =  blogPostRatingsRepository.findCountByRateByAndBlog(id,loginUser.getId());
        var rateobj = new BlogPostRatingsDto();
        if(rateCount == 0)
        {
            var blogItem = blogPostItemsRepository.getOne(id);
            var blogDto = new BlogPostItemsDto();
            mapper.map(blogItem,blogDto);
            rateobj.setBlogItem(blogDto);



            rateobj.setRateBy(loginUser);
            rateobj.setRate(0);
        }
        else
        {
          var  rateobjRetrieved = blogPostRatingsRepository.findByBlog(id, loginUser.getId());
          mapper.map(rateobjRetrieved,rateobj);

        }
        model.addAttribute("rateobject",rateobj);

        return  DEFAULT_PAGE;
    }


    @PostMapping("/save/rate")
    public RedirectView saveRateItem(@ModelAttribute BlogPostRatingsDto blogPostRatings, Model model)
    {
        var blogRate = new BlogPostRatings();
        var mapper = new ModelMapper();
        mapper.map(blogPostRatings,blogRate);
        blogPostRatingsRepository.saveAndFlush(blogRate);


        var avgRate=  blogPostRatingsRepository.findAvgByBlog(blogRate.getBlogItem().getId());

        var blogItem = blogPostItemsRepository.findOneById(blogRate.getBlogItem().getId());

        blogItem.setAvgRate(avgRate);

        blogPostItemsRepository.saveAndFlush(blogItem);

        return new RedirectView("/details/article/"+blogRate.getBlogItem().getId());
    }



}
