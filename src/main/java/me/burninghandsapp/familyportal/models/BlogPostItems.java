package me.burninghandsapp.familyportal.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class BlogPostItems implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int Id;

    @ManyToOne
    private Categories category;

    @Column(length = 150)
    private String  Title;

    @Column(columnDefinition="TEXT")
    private  String Description;

    @Column(length = 150)
    private  String Website;

    @ManyToOne
    private  User Author;

    @Column(name = "DateCreated", columnDefinition = "TIMESTAMP")
    private LocalDateTime DateCreated;

    private  Integer AvgRate;

}
