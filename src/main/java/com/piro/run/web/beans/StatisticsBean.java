package com.piro.run.web.beans;

import com.piro.run.dto.LegStatisticsDto;
import com.piro.run.service.StatisticsService;
import com.piro.run.service.impl.InstanceServiceImpl;
import com.piro.run.service.impl.StatisticsServiceImpl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ppirovski on 5/12/15. In Code we trust
 */
public class StatisticsBean implements Serializable {

    public final long serialVersionUID = 1638523944826114228L;

    private transient StatisticsService statisticsService;

    private String selectedStatistics;

    private List<LegStatisticsDto> legStatistics;

    public StatisticsBean(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        selectedStatistics = "alabala";
    }

    public List<LegStatisticsDto> getLegStatistics(){
        if(legStatistics == null){
            legStatistics = statisticsService.getLegStatistics();
        }
        return legStatistics;
    }

    public String getSelectedStatistics() {
        return selectedStatistics;
    }

    public void setSelectedStatistics(String selectedStatistics) {
        this.selectedStatistics = selectedStatistics;
        clearOldStatistics();
    }

    private void clearOldStatistics(){
        legStatistics = null;
    }
}
