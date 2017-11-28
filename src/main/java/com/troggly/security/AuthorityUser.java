package com.troggly.security;

import com.troggly.enums.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Vlad on 27.07.2017.
 */
public class AuthorityUser implements GrantedAuthority {
    private static final long serialVersionUID = -2381291913383719743L;
    private String authorizedUser;

    public AuthorityUser(Role role) {
        this.authorizedUser = role.name();
    }

    @Override
    public String getAuthority() {
        return this.authorizedUser;
    }
}
