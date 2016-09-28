package com.piro.run.web.beans;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.dto.InstanceDto;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.Leg;
import com.piro.run.enums.Type;
import com.piro.run.exception.ResourceNotFoundException;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.InstanceService;
import com.piro.run.service.LegService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import com.piro.run.service.impl.InstanceServiceImpl;
import com.piro.run.service.impl.LegServiceImpl;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public class LegsBean implements Serializable {

    public final long serialVersionUID = 6921821942126311321L;

    private transient CompetitionService competitionService;
    private transient InstanceService instanceService;
    private transient LegService legService;

    private CompetitionDto competitionDto;
    private InstanceDto instanceDto;
    private List<LegDto> legs;
    private LegDto forCreate;

    public LegsBean(InstanceService instanceService, LegService legService, CompetitionService competitionService) throws ResourceNotFoundException {
        this.competitionService = competitionService;
        this.instanceService = instanceService;
        this.legService = legService;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String instanceIdStr = parameterMap.get("instanceId");

        Long instanceId = -1L;
        if(!StringUtils.isEmpty(instanceIdStr)){
            try {
                instanceId = Long.valueOf(instanceIdStr);
            }
            catch (NumberFormatException e){
                throw new ResourceNotFoundException();
            }
        }

        if(instanceId != -1) {
            instanceDto = instanceService.getById(instanceId);
            if(instanceDto == null){
                throw new ResourceNotFoundException();
            }
            competitionDto = competitionService.getById(instanceDto.getCompetitionId());
        }else{
            throw new IllegalArgumentException("cannot find instance");
        }

        forCreate = new LegDto();
        forCreate.setInstanceId(instanceId);
    }

    public List<LegDto> getLegs() {
        if(legs == null){
            legs = legService.listByInstance(instanceDto);
        }
        return legs;
    }

    public InstanceDto getInstanceDto() {
        return instanceDto;
    }

    public void onRowEdit(RowEditEvent event) {
        LegDto toUpdate = (LegDto)event.getObject();
        legService.update(toUpdate);
        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("legEdited"), "" );
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(LegDto forDelete){
        if(forDelete.getResultsCount() > 0){
            return; //shouldn't delete leg that has results
        }
        legService.delete(forDelete.getId());
        legs.remove(forDelete);
        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("legDeleted"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void createNewLeg(){
        if(forCreate == null){
            throw new IllegalStateException("Leg for create does not exist");
        }
        LegDto created = legService.createNew(forCreate);
        legs.add(created);
        forCreate = new LegDto();
        forCreate.setInstanceId(instanceDto.getId());

        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("newLegCreated"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().execute("PF('createDialog').hide();");
    }

    public LegDto getForCreate() {
        return forCreate;
    }

    public void setForCreate(LegDto forCreate) {
        this.forCreate = forCreate;
    }

    public CompetitionDto getCompetitionDto() {
        return competitionDto;
    }

    public void redirectEdit(String id) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("checkPointsCRUD.jsf?legId="+id);
    }

    public void redirectResults(String id) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("resultsCRUD.jsf?legId="+id);
    }

    public List<SelectItem> getTypes(){
        List<SelectItem> result = new ArrayList<>();
        for(Type t: Type.values()){
            result.add(new SelectItem(t, t.toString()));
        }
        return result;
    }
}
