package org.recap.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sheiks on 17/01/17.
 */
public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = -4777124807325532850L;

    private String userid;

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
     * @param userid      the userid
     * @param authorities the authorities
     */
    public AppUserDetails(String userid, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.userid = userid;
        this.authorities = authorities;
        this.roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            this.roles.add(authority.getAuthority());
        }
    }

    /**
     * Gets userid.
     *
     * @return the userid
     */
    public String getUserid() {
        return userid;
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
        return userid;
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
        return "UserDetails [userid=" + userid + ", authorities=" + roles.toString() + ", isAccountNonExpired()="
                + isAccountNonExpired() + ", isAccountNonLocked()=" + isAccountNonLocked()
                + ", isCredentialsNonExpired()=" + isCredentialsNonExpired() + ", isEnabled()=" + isEnabled() + "]";
    }
}


