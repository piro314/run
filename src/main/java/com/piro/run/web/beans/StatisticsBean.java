package com.piro.run.web.beans;

import com.piro.run.dto.statistics.LegStatisticsDto;
import com.piro.run.dto.statistics.RecordsDto;
import com.piro.run.enums.Type;
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

    private List<RecordsDto> maleRecordsRun;
    private List<RecordsDto> maleRecordsBike;
    private List<RecordsDto> femaleRecordsRun;
    private List<RecordsDto> femaleRecordsBike;

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

    private void clearOldStatistics(){
        legStatistics = null;
        maleRecordsRun = null;
        maleRecordsBike = null;
        femaleRecordsRun = null;
        femaleRecordsBike = null;
    }
}
