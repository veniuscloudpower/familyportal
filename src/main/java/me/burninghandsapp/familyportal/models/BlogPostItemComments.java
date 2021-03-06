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
    private  int id;

    @ManyToOne
    private BlogPostItems blogItem;

    @ManyToOne
    private  User author;

    @Column(columnDefinition="TEXT")
    private String commentText;

    @Column(name = "DateCreated", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreated;
}
