package com.piro.run.service.impl;

import com.piro.run.assembler.LegAssembler;
import com.piro.run.assembler.impl.LegAssemblerImpl;
import com.piro.run.dao.LegRepository;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.dto.LegDto;
import com.piro.run.dto.LegStatisticsDto;
import com.piro.run.entity.Leg;
import com.piro.run.enums.Type;
import com.piro.run.service.StatisticsService;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Created by ppirovski on 5/12/15. In Code we trust
 */
public class StatisticsServiceImpl implements StatisticsService {

    private LegRepository legRepository;
    private LegAssembler legAssembler;

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


    @Required
    public void setLegRepository(LegRepository legRepository) {
        this.legRepository = legRepository;
    }


    @Required
    public void setLegAssembler(LegAssembler legAssembler) {
        this.legAssembler = legAssembler;
    }
}
