package com.piro.run.dto;

import com.piro.run.entity.Instance;

import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CompetitionDto {

    private Long id;
    private String name;
    private String url;
    private String description;
    private List<InstanceDto> instances;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InstanceDto> getInstances() {
        return instances;
    }

    public void setInstances(List<InstanceDto> instances) {
        this.instances = instances;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("id=").append(id).append("\n");
        builder.append("name=").append(name).append("\n");

        return builder.toString();
    }
}
