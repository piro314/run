package com.piro.run.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */

@Entity
@Table(name="competitions")
public class Instance {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<Leg> legs;
    private Competition competition;

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

    @Column(name="start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name="end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy="instance")
    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="competition_id")
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
