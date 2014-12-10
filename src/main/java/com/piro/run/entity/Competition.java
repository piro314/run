package com.piro.run.entity;

import javax.persistence.*;

/**
 * Created by ppirovski on 12/4/14. In Code we trust
 */

@Entity
public class Competition {

    private Long id;
    private String name;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
