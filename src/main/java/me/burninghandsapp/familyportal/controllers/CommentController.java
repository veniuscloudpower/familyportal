package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.models.BlogPostItemComments;
import me.burninghandsapp.familyportal.repositories.BlogPostItemCommentsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CommentController extends  BaseController {

    @Autowired
    private BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    @Autowired
    private BlogPostItemsRepository blogPostItemsRepository;

    @PostMapping("/comments/add")
    public RedirectView CommentAdd(String commenttext, int blogId , Model model)
    {
        getLoginUser();
        BlogPostItemComments comments = new BlogPostItemComments();
        comments.setCommentText(commenttext);
        comments.setAuthor(loginUser);

        comments.setDateCreated(getNow());
        var blog = blogPostItemsRepository.findById(blogId);

        if (blog.isPresent())
        {
            comments.setBlogItem(blog.get());
        }

        blogPostItemCommentsRepository.saveAndFlush(comments);

        var id = String.valueOf( blogId);
        return new RedirectView("/details/article/"+id);
    }
}
