package me.burninghandsapp.familyportal.modeldto;


import lombok.Getter;
import lombok.Setter;
import me.burninghandsapp.familyportal.models.Categories;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class BlogPostItemsDto implements Serializable {


    private  int id;


    private Categories category;


    private String  title;


    private  String description;


    private  String website;


    private UserDto author;


    private LocalDateTime dateCreated;

    private  Integer avgRate;
}
