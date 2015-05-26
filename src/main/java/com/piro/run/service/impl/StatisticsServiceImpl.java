package com.piro.run.service.impl;

import com.piro.run.assembler.LegAssembler;
import com.piro.run.assembler.impl.LegAssemblerImpl;
import com.piro.run.dao.LegRepository;
import com.piro.run.dao.ResultRepository;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.dto.LegDto;
import com.piro.run.dto.statistics.LegStatisticsDto;
import com.piro.run.dto.statistics.RecordsDto;
import com.piro.run.dto.statistics.UserResultDto;
import com.piro.run.entity.Leg;
import com.piro.run.enums.Type;
import com.piro.run.service.StatisticsService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ppirovski on 5/12/15. In Code we trust
 */
public class StatisticsServiceImpl implements StatisticsService {

    private LegRepository legRepository;
    private LegAssembler legAssembler;
    private ResultRepository resultRepository;

    @Override
    public List<LegStatisticsDto> getLegStatistics() {

        List<LegStatisticsDto> result = legRepository.getLegStatistics();

        for(LegStatisticsDto dto : result){
            dto.setType(Type.fromInt(dto.getTypeInt()));
            dto.setGradePlus((double)dto.getdPlus()/dto.getDistance());
            dto.setGradeMinus((double)dto.getdMinus()/dto.getDistance());
        }
        return result;
    }

    @Override
    public List<RecordsDto> getRecords(boolean male, Type type) {

        // records are ordered by (competitionName, LegName)
        List<RecordsDto> records = resultRepository.getRecords(male, type.getValue());
        if(records == null || records.isEmpty()){
            return new ArrayList<>();
        }

        List<RecordsDto> forRemove = new ArrayList<>();
        RecordsDto best  = records.get(0);
        boolean first = true;

        for(RecordsDto record : records){
            if(first){
                first = false;
                continue;
            }

            if(record.getCompetitionName().equals(best.getCompetitionName()) &&
                    record.getLegName().equals(best.getLegName()) &&
                    record.getDistance() == best.getDistance()){
                // if it's the same track we should find the instance with fastest time

                if(record.getTime() >= best.getTime()){
                    //if current record is not better than the best so far
                    forRemove.add(record);
                    continue;
                }
                // otherwise
                forRemove.add(best);
            }
            //if current is not the same track or is better than the best so far
            best = record;

        }
        records.removeAll(forRemove);

        return records;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResultDto> getUserResults(String username) {
        return resultRepository.getUserResults(username);
    }



    @Required
    public void setLegRepository(LegRepository legRepository) {
        this.legRepository = legRepository;
    }


    @Required
    public void setLegAssembler(LegAssembler legAssembler) {
        this.legAssembler = legAssembler;
    }


    @Required
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
}
