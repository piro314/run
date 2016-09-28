package com.piro.run.web.beans;

import com.piro.run.dto.CheckPointDto;
import com.piro.run.dto.CompetitionDto;
import com.piro.run.dto.InstanceDto;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.CheckPoint;
import com.piro.run.exception.ResourceNotFoundException;
import com.piro.run.service.CheckPointService;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.InstanceService;
import com.piro.run.service.LegService;
import com.piro.run.service.impl.CheckPointServiceImpl;
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
import java.util.*;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public class CheckPointsBean implements Serializable{

    public final long serialVersionUID = 2131421922126311278L;

    private transient CompetitionService competitionService;
    private transient InstanceService instanceService;
    private transient LegService legService;
    private transient CheckPointService checkPointService;

    private CompetitionDto competitionDto;
    private InstanceDto instanceDto;
    private LegDto legDto;
    private List<CheckPointDto> checkPoints;
    private CheckPointDto forCreate;
    private Long copyLegId;


    public CheckPointsBean(InstanceService instanceService,
                           CompetitionService competitionService,
                           LegService legService,
                           CheckPointService checkPointService) throws ResourceNotFoundException {

        this.competitionService = competitionService;
        this.instanceService = instanceService;
        this.legService = legService;
        this.checkPointService = checkPointService;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String legIdStr = parameterMap.get("legId");

        Long legId = -1L;
        if(!StringUtils.isEmpty(legIdStr)){
            try {
                legId = Long.valueOf(legIdStr);
            }
            catch (NumberFormatException e){
                throw new ResourceNotFoundException();
            }
        }

        if(legId != -1) {
            legDto = legService.getById(legId);
            if(legDto == null){
                throw new ResourceNotFoundException();
            }
            instanceDto = instanceService.getById(legDto.getInstanceId());
            competitionDto = competitionService.getById(instanceDto.getCompetitionId());
        }else{
            throw new IllegalArgumentException("cannot find leg");
        }

        forCreate = new CheckPointDto();
        forCreate.setLegId(legId);

    }

    public List<CheckPointDto> getCheckPoints() {
        if(checkPoints == null){
            checkPoints = checkPointService.getByLeg(legDto);
            Collections.sort(checkPoints);
        }
        return checkPoints;
    }

    public CompetitionDto getCompetitionDto() {
        return competitionDto;
    }

    public InstanceDto getInstanceDto() {
        return instanceDto;
    }

    public LegDto getLegDto() {
        return legDto;
    }

    public void onRowEdit(RowEditEvent event) {
        CheckPointDto toUpdate = (CheckPointDto)event.getObject();
        checkPointService.update(toUpdate);
        Collections.sort(checkPoints);
        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("checkPointEdited"), "" );
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("editCancelled"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(CheckPointDto forDelete){
        checkPointService.delete(forDelete.getId());
        checkPoints.remove(forDelete);
        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("checkPointDeleted"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void createNewCheckPoint(){
        if(forCreate == null){
            throw new IllegalStateException("Check Point for create does not exist");
        }

        CheckPointDto created = checkPointService.createNew(forCreate);
        checkPoints.add(created);
        Collections.sort(checkPoints);
        forCreate = new CheckPointDto();
        forCreate.setLegId(legDto.getId());

        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("newCheckPointCreated"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().execute("PF('createDialog').hide();");
    }

    public CheckPointDto getForCreate() {
        return forCreate;
    }

    public void setForCreate(CheckPointDto forCreate) {
        this.forCreate = forCreate;
    }

    public Long getCopyLegId() {
        return copyLegId;
    }

    public void setCopyLegId(Long copyLegId) {
        this.copyLegId = copyLegId;
    }

    public void cloneCheckPoints(){
        legService.cloneCheckPoints(this.getCopyLegId(), legDto.getId());
        checkPoints = null;

        ResourceBundle bundle = ResourceBundle.getBundle("Text", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage msg = new FacesMessage(bundle.getString("checkPointsCloned"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().execute("PF('cloneDialog').hide();");
    }

    public List<SelectItem> getSelectLegs(){
        List<SelectItem> items = new ArrayList<>();
        List<LegDto> legs = legService.listByInstance(instanceDto);
        for(LegDto leg : legs){
            if(leg.getId().longValue() == legDto.getId().longValue()){
                continue;
            }
            SelectItem item = new SelectItem(leg.getId(), leg.getName());
            items.add(item);
        }

        return items;
    }

    public boolean hasAnotherLast(long current){
        for(CheckPointDto dto : checkPoints){
            if(dto.getId() == current){
                continue;
            }
            if(dto.isLast()){
                return true;
            }
        }
        return false;
    }
}
