package com.piro.run.entity;

import javax.persistence.*;

/**
 * Created by ppirovski on 12/5/14. In Code we trust
 */

//@Entity
//@Table(name = "authorities")
public class Authority {

    private Long id;
    private String authority;
    private User user;


    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "authority")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
