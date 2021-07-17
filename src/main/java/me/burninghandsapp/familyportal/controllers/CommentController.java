package me.burninghandsapp.familyportal.controllers;

import me.burninghandsapp.familyportal.modeldto.UserDto;
import me.burninghandsapp.familyportal.models.BlogPostItemComments;
import me.burninghandsapp.familyportal.models.User;
import me.burninghandsapp.familyportal.repositories.BlogPostItemCommentsRepository;
import me.burninghandsapp.familyportal.repositories.BlogPostItemsRepository;
import me.burninghandsapp.familyportal.repositories.CategoriesRepository;
import me.burninghandsapp.familyportal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CommentController extends  BaseController {

    private final BlogPostItemCommentsRepository blogPostItemCommentsRepository;

    private final BlogPostItemsRepository blogPostItemsRepository;

    @Autowired
    public CommentController(CategoriesRepository categoryRepository, UserRepository userRepository, BlogPostItemCommentsRepository blogPostItemCommentsRepository, BlogPostItemsRepository blogPostItemsRepository) {
        super(categoryRepository, userRepository);
        this.loginUser = new UserDto();
        this.blogPostItemCommentsRepository = blogPostItemCommentsRepository;
        this.blogPostItemsRepository = blogPostItemsRepository;
    }

    @PostMapping("/comments/add")
    public RedirectView commentAdd(String commentText, int blogId , Model model)
    {
        getLoginUser();
        var comments = new BlogPostItemComments();
        comments.setCommentText(commentText);

        var author = new User();
        mapper.map(loginUser,author);

        comments.setAuthor(author);

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
