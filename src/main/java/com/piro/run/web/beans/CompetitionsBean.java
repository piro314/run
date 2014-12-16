package com.piro.run.web.beans;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Required;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public class CompetitionsBean implements Serializable{

    private transient CompetitionService competitionService;

    private List<CompetitionDto> competitions;

    public CompetitionsBean(){

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
        System.out.println("adasdasdasdasddfasfsdfasdf");
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setCompetitions(List<CompetitionDto> competitions) {
        this.competitions = competitions;
    }

    @Required
    public void setCompetitionService(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }
}
