package com.piro.run.dto;

import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Instance;
import com.piro.run.enums.Type;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class LegDto implements Serializable, Comparable<LegDto>{

    public final long serialVersionUID = 2727221942188311381L;

    private Long id;
    private String name;
    private int distance;
    private int dPlus;
    private int dMinus;
    private int highest;
    private int lowest;
    private List<CheckPointDto> checkPoints;
    private Long instanceId;
    private long resultsCount;
    private Type type;

    @Pattern(regexp="(\\d)+:(\\d{1,4})(,(\\d)+:(\\d{1,4}))*")
    private String profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getdPlus() {
        return dPlus;
    }

    public void setdPlus(int dPlus) {
        this.dPlus = dPlus;
    }

    public int getdMinus() {
        return dMinus;
    }

    public void setdMinus(int dMinus) {
        this.dMinus = dMinus;
    }

    public int getHighest() {
        return highest;
    }

    public void setHighest(int highest) {
        this.highest = highest;
    }

    public int getLowest() {
        return lowest;
    }

    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    public List<CheckPointDto> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<CheckPointDto> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public long getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(long resultsCount) {
        this.resultsCount = resultsCount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public int compareTo(LegDto legDto) {
        if(this.getType().getValue() < legDto.getType().getValue()){
            return -1;
        }
        if(this.getType().getValue() > legDto.getType().getValue()){
            return 1;
        }
        if(this.getDistance() < legDto.getDistance()){
            return -1;
        }
        if(this.getDistance() > legDto.getDistance()){
            return 1;
        }
        return 0;
    }
}
