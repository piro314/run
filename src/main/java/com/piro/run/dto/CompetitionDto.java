package com.piro.run.dto;

import com.piro.run.entity.Instance;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CompetitionDto  implements Serializable{

    public final long serialVersionUID = 1921221942117311391L;

    private Long id;
    private String name;
    private String url;
    private String description;
    private List<InstanceDto> instances;
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("id=").append(id).append("\n");
        builder.append("name=").append(name).append("\n");

        return builder.toString();
    }
}
