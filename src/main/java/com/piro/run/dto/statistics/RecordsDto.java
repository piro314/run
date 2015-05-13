package com.piro.run.dto.statistics;

import java.io.Serializable;

/**
 * Created by ppirovski on 5/13/15. In Code we trust
 */
public class RecordsDto implements Serializable {

    public final long serialVersionUID = 9188381442138912348L;

    private String competitionName;
    private String instanceName;
    private String legName;
    private String participantName;
    private int distance;
    private long time;

    public RecordsDto(String competitionName, String instanceName, String legName, String participantName, int distance, long time) {
        this.competitionName = competitionName;
        this.instanceName = instanceName;
        this.legName = legName;
        this.participantName = participantName;
        this.distance = distance;
        this.time = time;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getLegName() {
        return legName;
    }

    public void setLegName(String legName) {
        this.legName = legName;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
