package com.piro.run.web.beans;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.dto.InstanceDto;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.InstanceService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import com.piro.run.service.impl.InstanceServiceImpl;
import org.springframework.util.StringUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by ppirovski on 12/18/14. In Code we trust
 */
public class InstancesBean implements Serializable {

    private transient InstanceService instanceService;
    private transient CompetitionService competitionService;

    private CompetitionDto competitionDto;
    private List<InstanceDto> instances;

    public InstancesBean(CompetitionService competitionService, InstanceService instanceService){
        this.competitionService = competitionService;
        this.instanceService = instanceService;


        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String competitionIdStr = parameterMap.get("competitionId");

        Long competitionId = -1L;
        if(!StringUtils.isEmpty(competitionIdStr)){
            try {
                competitionId = Long.valueOf(competitionIdStr);
            }
            catch (NumberFormatException e){}
        }

        if(competitionId != -1) {
            competitionDto = competitionService.getById(competitionId);
        }else{
            throw new IllegalArgumentException("cannot find competition");
        }

    }

    public List<InstanceDto> getInstances() {
        if(instances == null){
            instances = instanceService.listByCompetition(getCompetitionDto());
        }
        return instances;
    }

    public void setInstances(List<InstanceDto> instances) {
        this.instances = instances;
    }

    public CompetitionDto getCompetitionDto() {
        return competitionDto;
    }

    public void setCompetitionDto(CompetitionDto competitionDto) {
        this.competitionDto = competitionDto;
    }

}
