package com.piro.run.entity;

import javax.persistence.*;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */

@Entity
@Table(name="results")
public class Result {

    private Long id;
    private CheckPoint checkPoint;
    private Long time;
    private Participant participant;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="check_point_id")
    public CheckPoint getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(CheckPoint checkPoint) {
        this.checkPoint = checkPoint;
    }

    @Column(name="time")
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }





    @ManyToOne
    @JoinColumn(name="participant_id")
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
