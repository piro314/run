package com.piro.run.dto.statistics;

import com.piro.run.enums.Type;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ppirovski on 5/12/15. In Code we trust
 */
public class LegStatisticsDto implements Serializable {

    public final long serialVersionUID = 6127321942188911341L;

    private Long id;
    private String name;
    private int distance;
    private int dPlus;
    private int dMinus;
    private int highest;
    private int lowest;
    private String instanceName;
    private String competitionName;
    private double gradePlus;
    private double gradeMinus;
    private int typeInt;
    private Type type;

    public LegStatisticsDto(Long id, String name, int distance, int dPlus, int dMinus, int highest, int lowest, String instanceName, String competitionName, int typeInt) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.dPlus = dPlus;
        this.dMinus = dMinus;
        this.highest = highest;
        this.lowest = lowest;
        this.instanceName = instanceName;
        this.competitionName = competitionName;
        this.typeInt = typeInt;
    }

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

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public double getGradePlus() {
        return gradePlus;
    }

    public void setGradePlus(double gradePlus) {
        this.gradePlus = gradePlus;
    }

    public double getGradeMinus() {
        return gradeMinus;
    }

    public void setGradeMinus(double gradeMinus) {
        this.gradeMinus = gradeMinus;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
