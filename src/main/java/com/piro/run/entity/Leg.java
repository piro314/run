package com.piro.run.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */

@Entity
@Table(name="legs")
public class Leg {

    private Long id;
    private String name;
    private int distance;
    private int dPlus;
    private int dMinus;
    private int highest;
    private int lowest;
    private List<CheckPoint> checkPoints;
    private Instance instance;


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

    @Column(name="distance")
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Column(name="d_plus")
    public int getdPlus() {
        return dPlus;
    }

    public void setdPlus(int dPlus) {
        this.dPlus = dPlus;
    }

    @Column(name="d_minus")
    public int getdMinus() {
        return dMinus;
    }

    public void setdMinus(int dMinus) {
        this.dMinus = dMinus;
    }

    @Column(name="highest")
    public int getHighest() {
        return highest;
    }

    public void setHighest(int highest) {
        this.highest = highest;
    }

    @Column(name="lowest")
    public int getLowest() {
        return lowest;
    }

    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="leg")
    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    @ManyToOne
    @JoinColumn(name="instance_id")
    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

}
