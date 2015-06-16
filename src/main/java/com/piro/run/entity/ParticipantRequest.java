package com.piro.run.entity;

import javax.persistence.*;

/**
 * Created by ppirovski on 6/12/15. In Code we trust
 */

@Entity
@Table(name="participant_requests")
public class ParticipantRequest {

    private Long id;
    private Participant participant;
    private User user;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name="participant_id")
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @ManyToOne()
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
