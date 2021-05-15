package me.burninghandsapp.familyportal.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {

    public  UserView(String userName , String avatar,String firstName,String lastName, Long id, boolean enabled,String role){
        this.avatar = avatar;
        this.userName= userName;
        this.firstName= firstName;
        this.lastName=lastName;
        this.id=id;
        this.enabled = enabled;
        this.role = role;
    }

    private String avatar;
    private String userName;
    private String firstName;
    private String lastName;
    private Long id;
    private boolean enabled;
    private String role;


}
