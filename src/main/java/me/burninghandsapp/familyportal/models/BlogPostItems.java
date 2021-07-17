package me.burninghandsapp.familyportal.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
public class BlogPostItems implements Serializable {


    public  BlogPostItems(){
        comments =  new ArrayList<>();
    }


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int id;


    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName="id")
    private Categories category;

    @Column(length = 150)
    private String  title;

    @Column(columnDefinition="TEXT")
    private  String description;

    @Column(length = 1500)
    private  String website;

    @ManyToOne
    @JoinColumn(name="author_user_id", referencedColumnName="user_id")
    private  User author;

    @Column(name = "dateCreated", columnDefinition = "DATETIME")
    private LocalDateTime dateCreated;

    private  Integer avgRate;

    @OneToMany(mappedBy = "blogItem")
    private List<BlogPostItemComments> comments;

}
