package me.burninghandsapp.familyportal.controllers;


import me.burninghandsapp.familyportal.modeldto.BlogPostItemsDto;
import me.burninghandsapp.familyportal.models.BlogPostAttachment;
import me.burninghandsapp.familyportal.models.BlogPostItems;
import me.burninghandsapp.familyportal.repositories.BlogPostAttachmentRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Base64;

@Controller
public class BlogController extends  BaseController {

    private final BlogPostItemsRepository blogPostItemsRepository;

    private final CategoriesRepository categoriesRepository;
    
    private final BlogPostAttachmentRepository blogPostAttachmentRepository;

    public static final  String BLOG_POST_DETAILS_PAGE = "pages/blogpostdetails :: main";

    public static final  String NEW_POST_PAGE = "pages/newpost :: main";

    public  static final String EDIT_POST_PAGE = "pages/editpost :: main";


    @Autowired
    public BlogController(CategoriesRepository categoryRepository, UserRepository userRepository, 
                         BlogPostItemsRepository blogPostItemsRepository, CategoriesRepository categoriesRepository,
                         BlogPostAttachmentRepository blogPostAttachmentRepository) {
        super(categoryRepository, userRepository);
        this.blogPostItemsRepository = blogPostItemsRepository;
        this.categoriesRepository = categoriesRepository;
        this.blogPostAttachmentRepository = blogPostAttachmentRepository;
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
    public  RedirectView newPost(@ModelAttribute BlogPostItemsDto blogPostItems, 
                                @RequestParam(value = "attachments", required = false) MultipartFile[] attachments,
                                Model model) throws IOException
    {
        getLoginUser();
        blogPostItems.setAuthor(loginUser);
        var blogItem = new BlogPostItems();

        mapper.map(blogPostItems,blogItem);

        blogItem.setDateCreated(getNow());
        blogItem.setAvgRate(0);
        blogPostItemsRepository.saveAndFlush(blogItem);
        
        // Handle file attachments
        if (attachments != null) {
            for (MultipartFile file : attachments) {
                if (!file.isEmpty()) {
                    var attachment = new BlogPostAttachment();
                    attachment.setBlogPost(blogItem);
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setContentType(file.getContentType());
                    attachment.setFileSize(file.getSize());
                    
                    var fileBytes = file.getBytes();
                    var fileBase64 = Base64.getMimeEncoder().encodeToString(fileBytes);
                    attachment.setFileData(fileBase64);
                    
                    blogPostAttachmentRepository.save(attachment);
                }
            }
        }
        
        var id = String.valueOf( blogItem.getId());

        var category = blogItem.getCategory();
        category.setHasArticles(true);
        categoriesRepository.saveAndFlush(category);

        return new RedirectView("/details/article/"+id);
    }

    @PostMapping("/save/article")
    public RedirectView editPost(@ModelAttribute BlogPostItemsDto blogPostItems,
                                @RequestParam(value = "attachments", required = false) MultipartFile[] attachments,
                                Model model) throws IOException
    {
        getLoginUser();
        blogPostItems.setAuthor(loginUser);

        var blogPost = new BlogPostItems();
        mapper.map(blogPostItems,blogPost);

        blogPostItemsRepository.saveAndFlush(blogPost);
        
        // Handle file attachments
        if (attachments != null) {
            for (MultipartFile file : attachments) {
                if (!file.isEmpty()) {
                    var attachment = new BlogPostAttachment();
                    attachment.setBlogPost(blogPost);
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setContentType(file.getContentType());
                    attachment.setFileSize(file.getSize());
                    
                    var fileBytes = file.getBytes();
                    var fileBase64 = Base64.getMimeEncoder().encodeToString(fileBytes);
                    attachment.setFileData(fileBase64);
                    
                    blogPostAttachmentRepository.save(attachment);
                }
            }
        }
        
        var id = String.valueOf( blogPost.getId());
        return new RedirectView("/details/article/"+id);
    }
    
    @GetMapping("/attachment/download/{id}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable(value="id") Integer id) {
        var attachment = blogPostAttachmentRepository.findById(id);
        
        if (attachment.isPresent()) {
            var file = attachment.get();
            byte[] fileBytes = Base64.getMimeDecoder().decode(file.getFileData());
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .contentLength(fileBytes.length)
                    .body(fileBytes);
        }
        
        return ResponseEntity.notFound().build();
    }
}
