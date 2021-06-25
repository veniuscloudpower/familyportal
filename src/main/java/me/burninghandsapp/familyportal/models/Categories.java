package me.burninghandsapp.familyportal.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Categories implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int id;

    private  String categoryName;

    private  String categoryDescription;


    private  Boolean hasArticles;


}
