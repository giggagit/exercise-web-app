package com.giggagit.exam.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.giggagit.exam.GroupValidation.Password;
import com.giggagit.exam.GroupValidation.Profile;

/**
 * UserModel
 */
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(groups = Profile.class)
    private String username;

    @NotBlank(groups = Profile.class)
    private String firstname;

    @NotBlank(groups = Profile.class)
    private String lastname;

    @Email(groups = Profile.class)
    @NotBlank(groups = Profile.class)
    private String email;

    @NotBlank(groups = {Password.class})
    private String password;

    @Transient
    @NotBlank(groups = {Password.class})
    private String passwordNew;

    @Transient
    @NotBlank(groups = {Password.class})
    private String passwordConfirm;

    @NotBlank(groups = Profile.class)
    private String classroom;

    @ManyToMany
	private Set<RoleModel> roles;

    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL)
    private List<ScoreModel> scoreModel = new ArrayList<>();

    public void addScore(ScoreModel scoreModel) {
    	if (this.scoreModel == null) {
    		this.scoreModel = new ArrayList<>();
		}
    	
    	this.scoreModel.add(scoreModel);
    	scoreModel.setUserModel(this);
    }
    
    public void removeExam(ScoreModel scoreModel) {
        this.scoreModel.remove(scoreModel);
        scoreModel.setUserModel(null);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordNew() {
        return this.passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getClassroom() {
        return this.classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Set<RoleModel> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

    public List<ScoreModel> getScoreModel() {
        return this.scoreModel;
    }

    public void setScoreModel(List<ScoreModel> scoreModel) {
        this.scoreModel = scoreModel;
    }

}