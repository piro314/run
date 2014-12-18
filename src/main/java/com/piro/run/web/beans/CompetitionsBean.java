package com.piro.run.web.beans;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Required;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public class CompetitionsBean implements Serializable{

    private transient CompetitionService competitionService;

    private List<CompetitionDto> competitions;

    private CompetitionDto forCreate;

    public CompetitionsBean(){
        forCreate = new CompetitionDto();
    }


    public List<CompetitionDto> getCompetitions() {
        if(competitions == null){
            competitions = competitionService.getAllCompetitions();
        }
        return competitions;
    }

    public void onRowEdit(RowEditEvent event) {
        CompetitionDto toUpdate = (CompetitionDto)event.getObject();
        competitionService.update(toUpdate);
        FacesMessage msg = new FacesMessage("Competition Edited", "" );
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setCompetitions(List<CompetitionDto> competitions) {
        this.competitions = competitions;
    }

    public void redirect(String id) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("instances.jsf?competitionId="+id);

    }

    public void openTestDialog(){
        RequestContext.getCurrentInstance().openDialog("testDialog");
    }

    public void createNewCompetition(){
        if(forCreate != null){
            CompetitionDto created = competitionService.createNew(forCreate);
            competitions.add(created);
            forCreate = new CompetitionDto();
        }
        FacesMessage msg = new FacesMessage("New competition created", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().execute("PF('testDialog').hide();");

    }

    public CompetitionDto getForCreate() {
        return forCreate;
    }

    public void setForCreate(CompetitionDto forCreate) {
        this.forCreate = forCreate;
    }

    public void delete(CompetitionDto forDelete){
        competitionService.delete(forDelete.getId());
        competitions.remove(forDelete);
        FacesMessage msg = new FacesMessage("Competition deleted", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @Required
    public void setCompetitionService(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }
}
