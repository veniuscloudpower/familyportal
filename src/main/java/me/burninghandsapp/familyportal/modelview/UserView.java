package me.burninghandsapp.familyportal.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {


    private String avatar;
    private String userName;
    private String firstName;
    private String lastName;
    private Long id;
    private boolean enabled;
    private String role;


}
