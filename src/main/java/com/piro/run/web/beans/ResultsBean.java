package com.piro.run.web.beans;

import com.piro.run.dto.*;
import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Result;
import com.piro.run.service.*;
import com.piro.run.service.impl.CompetitionServiceImpl;
import com.piro.run.service.impl.InstanceServiceImpl;
import com.piro.run.service.impl.LegServiceImpl;
import com.piro.run.service.impl.ResultServiceImpl;
import com.piro.run.utils.TimeUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ppirovski on 4/16/15. In Code we trust
 */
public class ResultsBean implements Serializable {

    public final long serialVersionUID = 8231921922126912228L;

    private transient CompetitionService competitionService;
    private transient InstanceService instanceService;
    private transient LegService legService;
    private transient ResultService resultService;

    private CompetitionDto competitionDto;
    private InstanceDto instanceDto;
    private LegDto legDto;

    private List<ParticipantResultDto> results;
    private ParticipantResultDto forCreate;

    private List<String> columns;

    public ResultsBean(CompetitionService competitionService, InstanceService instanceService, LegService legService, ResultService resultService) {
        this.competitionService = competitionService;
        this.instanceService = instanceService;
        this.legService = legService;
        this.resultService = resultService;

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String legIdStr = parameterMap.get("legId");

        Long legId = -1L;
        if(!StringUtils.isEmpty(legIdStr)){
            try {
                legId = Long.valueOf(legIdStr);
            }
            catch (NumberFormatException e){}
        }

        if(legId != -1) {
            legDto = legService.getById(legId);
            instanceDto = instanceService.getById(legDto.getInstanceId());
            competitionDto = competitionService.getById(instanceDto.getCompetitionId());
        }else{
            throw new IllegalArgumentException("cannot find leg");
        }

        forCreate = new ParticipantResultDto();
        forCreate.setResults(new ArrayList<ResultDto>());
        columns = new ArrayList<>();
        this.createColumns();



    }

    public List<ParticipantResultDto> getResults() {
        if(results == null){
            results = resultService.getResultsByLegGroupByParticipant(legDto);
        }
        return results;
    }

    public LegDto getLegDto() {
        return legDto;
    }

    public CompetitionDto getCompetitionDto() {
        return competitionDto;
    }

    public InstanceDto getInstanceDto() {
        return instanceDto;
    }

    public List<String> getColumns() {
        return columns;
    }

    public ParticipantResultDto getForCreate() {
        return forCreate;
    }

    public void onRowEdit(RowEditEvent event) {
        ParticipantResultDto toUpdate = (ParticipantResultDto)event.getObject();
        resultService.update(toUpdate);
        for(ResultDto resultDto : toUpdate.getResults()){
            resultDto.setTime(TimeUtils.covertToMillis(resultDto.getHours(), resultDto.getMinutes(), resultDto.getSeconds()));
        }
        FacesMessage msg = new FacesMessage("Result Edited", "" );
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete(ParticipantResultDto forDelete){
        resultService.delete(forDelete);
        results.remove(forDelete);
        FacesMessage msg = new FacesMessage("Result deleted", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void createColumns() {

        for(CheckPointDto checkPoint : legDto.getCheckPoints()) {
            String key = checkPoint.getName();
            columns.add(key);
            ResultDto dto = new ResultDto();
            dto.setCheckPointName(key);
            dto.setCheckPointId(checkPoint.getId());
            forCreate.getResults().add(dto);
        }
    }

    public void createNewResult(){
        if(forCreate == null){
            throw new IllegalStateException("Result for create does not exist");
        }
        resultService.createNew(forCreate);
        results = null;

        forCreate = new ParticipantResultDto();
        forCreate.setResults(new ArrayList<ResultDto>());
        columns = new ArrayList<>();
        this.createColumns();

        FacesMessage msg = new FacesMessage("New Result created", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().execute("PF('createDialog').hide();");

    }

}
