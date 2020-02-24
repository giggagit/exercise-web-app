package com.giggagit.exam.Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomUserDetails
 */
public class CustomUserDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final UserModel UserModel;

    public CustomUserDetails(UserModel UserModel) {
        this.UserModel = UserModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleModel roles : this.UserModel.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roles.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.UserModel.getPassword();
    }

    @Override
    public String getUsername() {
        return this.UserModel.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserModel getUserModel() {
        return this.UserModel;
    }

}