package me.burninghandsapp.familyportal.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class BlogPostItemComments implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int Id;

    @ManyToOne
    private BlogPostItems BlogItem;

    @ManyToOne
    private  User Author;

    @Column(columnDefinition="TEXT")
    private String CommentText;

    @Column(name = "DateCreated", columnDefinition = "TIMESTAMP")
    private LocalDateTime DateCreated;
}
