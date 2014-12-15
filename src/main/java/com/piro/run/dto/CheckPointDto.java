package com.piro.run.dto;

import com.piro.run.entity.Leg;

import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CheckPointDto {

    private long id;
    private String name;
    private int distanceFromStart;
    private int altitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
