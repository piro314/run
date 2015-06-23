package com.piro.run.entity;

import javax.persistence.*;

/**
 * Created by ppirovski on 6/22/15. In Code we trust
 */

@Entity
@Table(name="final_results")
public class FinalResult {


    private Long cId;
    private Long iId;
    private Long lId;
    private Long pId;
    private String cName;
    private String iName;
    private String lName;
    private String pName;
    private int type;
    private boolean male;
    private int distance;
    private long time;

    @Column(name="c_id")
    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    @Column(name="i_id")
    public Long getiId() {
        return iId;
    }

    public void setiId(Long iId) {
        this.iId = iId;
    }

    @Column(name="l_id")
    public Long getlId() {
        return lId;
    }

    public void setlId(Long lId) {
        this.lId = lId;
    }

    @Column(name="p_id")
    @Id
    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    @Column(name="c_name")
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Column(name="i_name")
    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    @Column(name="l_name")
    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Column(name="p_name")
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @Column(name="type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name="is_male")
    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    @Column(name="distance")
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Column(name="time")
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
