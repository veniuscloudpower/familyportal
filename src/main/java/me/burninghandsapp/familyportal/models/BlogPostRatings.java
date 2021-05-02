package me.burninghandsapp.familyportal.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class BlogPostRatings implements Serializable {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int Id;

    @ManyToOne
    private BlogPostItems BlogItem;

    @ManyToOne
    private  User RateBy;

    private  Integer Rate;
}
