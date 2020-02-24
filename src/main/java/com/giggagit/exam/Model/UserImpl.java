package com.giggagit.exam.Model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * UserImpl
 */
public class UserImpl extends User {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String classroom;

    public UserImpl(String username, String password, Collection<? extends GrantedAuthority> authorities,
            Integer id, String firstname, String lastname, String email, String classroom) {
        super(username, password, authorities);
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.classroom = classroom;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassroom() {
        return this.classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

}