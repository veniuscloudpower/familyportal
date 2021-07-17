package me.burninghandsapp.familyportal.models;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Priority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {

    public User(String userName , String firstName,String lastName)
    {
        this.enabled = true;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.avatar = "/img/avatar.png";
        this.role = "admin";
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String userName;
    private String password;
    private String role;


    private boolean enabled;

    @Column(columnDefinition = "text")
    private  String avatar;

    private  String firstName;
    private  String lastName;

    private  String email;
    private  String mobile;


    public User() {
       // empty contractor without setting default values
    }
}
