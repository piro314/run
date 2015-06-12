package com.piro.run.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */

@Entity
@Table(name="check_points")
public class CheckPoint implements Comparable<CheckPoint>{

    private long id;
    private String name;
    private int distanceFromStart;
    private int altitude;
    private Leg leg;
    private List<Result> results;
    private boolean last;


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

    @ManyToOne
    @JoinColumn(name="leg_id")
    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="checkPoint", fetch = FetchType.EAGER)
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Column(name="is_last")
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public int compareTo(CheckPoint checkPoint) {
        return this.getDistanceFromStart() - checkPoint.getDistanceFromStart();
    }
}
