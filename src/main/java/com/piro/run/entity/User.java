package com.piro.run.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ppirovski on 12/5/14. In Code we trust
 */

@Entity
@Table(name = "users")
public class User {

    private String username;
    private String password;
    private boolean enabled;
    private List<String> authorities;

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name="authority")
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = {@JoinColumn(name="username")})
    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
