package com.piro.run.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */

@Entity
@Table(name="participants")
public class Participant implements Comparable<Participant>{

    private Long id;
    private String name;
    private String username;
    private String number;
    private List<Result> results;
    private boolean male;
    private String category;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="participant")
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Column(name="number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(Participant participant) {

        return this.getName().compareTo(participant.getName());
    }

    @Column(name="is_male")
    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    @Column(name="category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
