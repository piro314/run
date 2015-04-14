package com.piro.run.dto;

import com.piro.run.entity.Competition;
import com.piro.run.entity.Leg;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class InstanceDto implements Serializable{

    public final long serialVersionUID = 2931221342113611371L;

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<LegDto> legs;
    private Long competitionId;


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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<LegDto> getLegs() {
        return legs;
    }

    public void setLegs(List<LegDto> legs) {
        this.legs = legs;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }
}
