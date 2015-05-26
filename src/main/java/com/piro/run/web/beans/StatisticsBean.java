package com.piro.run.web.beans;

import com.piro.run.dto.statistics.LegStatisticsDto;
import com.piro.run.dto.statistics.RecordsDto;
import com.piro.run.dto.statistics.UserResultDto;
import com.piro.run.enums.Type;
import com.piro.run.service.StatisticsService;
import com.piro.run.service.impl.InstanceServiceImpl;
import com.piro.run.service.impl.StatisticsServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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

    private List<RecordsDto> maleRecordsRun;
    private List<RecordsDto> maleRecordsBike;
    private List<RecordsDto> femaleRecordsRun;
    private List<RecordsDto> femaleRecordsBike;

    private List<UserResultDto> userResults;

    public StatisticsBean(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        selectedStatistics = "legStatistics";
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

    public List<RecordsDto> getMaleRecordsRun() {
        if(maleRecordsRun == null){
            maleRecordsRun = statisticsService.getRecords(true, Type.RUN);
        }
        return maleRecordsRun;
    }

    public List<RecordsDto> getMaleRecordsBike() {
        if(maleRecordsBike == null){
            maleRecordsBike = statisticsService.getRecords(true, Type.BIKE);
        }
        return maleRecordsBike;
    }

    public List<RecordsDto> getFemaleRecordsRun() {
        if(femaleRecordsRun == null){
            femaleRecordsRun = statisticsService.getRecords(false, Type.RUN);
        }
        return femaleRecordsRun;
    }

    public List<RecordsDto> getFemaleRecordsBike() {
        if(femaleRecordsBike == null){
            femaleRecordsBike = statisticsService.getRecords(false, Type.BIKE);
        }
        return femaleRecordsBike;
    }

    public List<UserResultDto> getUserResults() {
        if(userResults == null){
            userResults = statisticsService.getUserResults(this.getCurrentUsername());
        }
        return userResults;
    }

    private void clearOldStatistics(){
        legStatistics = null;
        maleRecordsRun = null;
        maleRecordsBike = null;
        femaleRecordsRun = null;
        femaleRecordsBike = null;
        userResults = null;
    }

    private String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == null){
            return "anonymousUser";
        }
        if(principal instanceof String){
           return  (String)principal;
        }
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return "anonymousUser";
    }
}
