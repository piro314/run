package com.piro.run.dto;

import java.util.List;

/**
 * Created by ppirovski on 4/16/15. In Code we trust
 */
public class ParticipantResultDto {

    public final long serialVersionUID = 1234921921234912218L;

    private Long participantId;
    private String participantName;
    private String participantUsername;
    private List<ResultDto> results;
    private Long legId;

    public List<ResultDto> getResults() {
        return results;
    }

    public void setResults(List<ResultDto> results) {
        this.results = results;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantUsername() {
        return participantUsername;
    }

    public void setParticipantUsername(String participantUsername) {
        this.participantUsername = participantUsername;
    }

    public Long getLegId() {
        return legId;
    }

    public void setLegId(Long legId) {
        this.legId = legId;
    }
}
