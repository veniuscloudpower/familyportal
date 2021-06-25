package me.burninghandsapp.familyportal.modeldto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class BlogPostRatingsDto implements Serializable {

    private  int id;


    private BlogPostItemsDto blogItem;


    private UserDto rateBy;

    private  Integer rate;

}
