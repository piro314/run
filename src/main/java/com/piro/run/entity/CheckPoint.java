package com.piro.run.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */

@Entity
@Table(name="check_points")
public class CheckPoint {

    private long id;
    private String name;
    private int distanceFromStart;
    private int altitude;
    private List<Leg> legs;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="distance_from_start")
    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    @Column(name="altitude")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @ManyToMany(mappedBy="checkPoints")
    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
