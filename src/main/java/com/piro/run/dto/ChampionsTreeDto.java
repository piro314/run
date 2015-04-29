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
}
