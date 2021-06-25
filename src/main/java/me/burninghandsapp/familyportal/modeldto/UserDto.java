package me.burninghandsapp.familyportal.modeldto;

import lombok.Getter;
import lombok.Setter;
import me.burninghandsapp.familyportal.models.User;
import org.modelmapper.ModelMapper;
import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String role;


    private boolean enabled;

    private  String avatar;

    private  String firstName;
    private  String lastName;

    private  String email;
    private  String mobile;

    public  User getModelFromDto()
    {
        var mapper = new ModelMapper();
        var userMap = new User();
        mapper.map(this,userMap);
        return userMap;
    }

}
