package com.piro.run.dto.statistics;

import com.piro.run.utils.TimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ppirovski on 5/26/15. In Code we trust
 */
public class UserResultDto implements Serializable{

    public final long serialVersionUID = 4284922923234492219L;

    private String username;
    private String competitionName;
    private String instanceName;
    private String legName;
    private Long time;
    private Integer hours;
    private Integer minutes;
    private Integer seconds;
    private Date date;

    public UserResultDto(String username, String competitionName, String instanceName, String legName, Long time, Date date) {
        this.username = username;
        this.competitionName = competitionName;
        this.instanceName = instanceName;
        this.legName = legName;
        this.time = time;
        this.date = date;
        this.hours = TimeUtils.getHours(time);
        this.minutes = TimeUtils.getMinutes(time);
        this.seconds = TimeUtils.getSeconds(time);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
