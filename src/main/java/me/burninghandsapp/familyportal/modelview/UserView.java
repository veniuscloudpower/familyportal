package me.burninghandsapp.familyportal.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {

    public  UserView(String userName , String avatar,String firstName,String lastName){
        this.avatar = avatar;
        this.userName= userName;
        this.firstName= firstName;
        this.lastName=lastName;
    }

    private String avatar;
    private String userName;
    private String firstName;
    private String lastName;


}
