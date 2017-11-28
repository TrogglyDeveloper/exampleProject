package com.troggly.security;

import com.troggly.enums.Role;
import com.troggly.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vlad on 27.07.2017.
 */
public class AuthorizedUser implements UserDetails {
    private User user;
    private Collection<AuthorityUser> authorities = new ArrayList<>();

    public AuthorizedUser(User user) {
        this.user = user;
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new AuthorityUser(role));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return this.user.getLogin();
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
}
