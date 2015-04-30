package com.piro.run.dto;

import java.io.Serializable;

/**
 * Created by ppirovski on 4/29/15. In Code we trust
 */
public class ChampionsTreeDto implements Serializable{

    public final long serialVersionUID = 1728452358282913428L;

    private String name;
    private String id;
    private String type;
    private Long time;
    private String data;
    private String sex;
    private String category;

    public ChampionsTreeDto(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof ChampionsTreeDto )) return false;

        ChampionsTreeDto  that = (ChampionsTreeDto)obj;

        return (this.getId().equals(that.getId()) && this.getType().equals(that.getType()));

    }

    @Override
    public int hashCode() {
        return (this.getId()+this.getType()).hashCode();
    }
}
