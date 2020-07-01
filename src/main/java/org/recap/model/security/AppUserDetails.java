package org.recap.model.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sheiks on 17/01/17.
 */
@Getter
@Setter
public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = -4777124807325532850L;

    private String userId;

    private Collection<? extends GrantedAuthority> authorities;

    private List<String> roles;

    /**
     * Instantiates a new AppUserDetails object.
     */
    public AppUserDetails() {
        super();
    }

    /**
     * Instantiates a new AppUserDetails object with arguments.
     *
     * @param userId      the userId
     * @param authorities the authorities
     */
    public AppUserDetails(String userId, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.userId = userId;
        this.authorities = authorities;
        this.roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            this.roles.add(authority.getAuthority());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
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

    @Override
    public String toString() {
        return "UserDetails [userId=" + userId + ", authorities=" + roles.toString() + ", isAccountNonExpired()="
                + isAccountNonExpired() + ", isAccountNonLocked()=" + isAccountNonLocked()
                + ", isCredentialsNonExpired()=" + isCredentialsNonExpired() + ", isEnabled()=" + isEnabled() + "]";
    }
}


