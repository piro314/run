package com.piro.run.web.beans;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.service.CompetitionService;
import com.piro.run.service.impl.CompetitionServiceImpl;
import org.springframework.beans.factory.annotation.Required;

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

    public void setCompetitions(List<CompetitionDto> competitions) {
        this.competitions = competitions;
    }

    @Required
    public void setCompetitionService(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }
}
