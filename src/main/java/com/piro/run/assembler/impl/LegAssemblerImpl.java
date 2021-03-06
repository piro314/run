package com.piro.run.assembler.impl;

import com.piro.run.assembler.CheckPointAssembler;
import com.piro.run.assembler.LegAssembler;
import com.piro.run.dao.InstanceRepository;
import com.piro.run.dao.ResultRepository;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.Leg;
import com.piro.run.enums.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class LegAssemblerImpl extends BaseAssembler<Leg, LegDto>
                              implements LegAssembler {

    private static final Logger LOG = LoggerFactory.getLogger(LegAssemblerImpl.class);

    private CheckPointAssembler checkPointAssembler;

    private InstanceRepository instanceRepository;
    private ResultRepository resultRepository;

    @Override
    public Leg toEntity(LegDto dto) {
        LOG.debug("Transforming to entity dto with id = "+dto.getId());

        Leg entity = new Leg();

        entity.setName(dto.getName());
        entity.setCheckPoints(checkPointAssembler.toEntities(dto.getCheckPoints()));
        entity.setDistance(dto.getDistance());
        entity.setdMinus(dto.getdMinus());
        entity.setdPlus(dto.getdPlus());
        entity.setHighest(dto.getHighest());
        entity.setId(dto.getId());
        entity.setLowest(dto.getLowest());
        entity.setInstance(instanceRepository.findOne(dto.getInstanceId()));
        entity.setType(dto.getType().getValue());
        entity.setProfile(dto.getProfile());

        return entity;
    }

    @Override
    public LegDto toDto(Leg entity) {
        if(entity == null){
            return null;
        }
        LOG.debug("Transforming to dto entity with id = "+entity.getId());

        LegDto dto = new LegDto();

        dto.setLowest(entity.getLowest());
        dto.setId(entity.getId());
        dto.setHighest(entity.getHighest());
        dto.setdPlus(entity.getdPlus());
        dto.setDistance(entity.getDistance());
        dto.setdMinus(entity.getdMinus());
        List<CheckPointDto> checkPointDtos= checkPointAssembler.toDtos(entity.getCheckPoints());
        Collections.sort(checkPointDtos);
        dto.setCheckPoints(checkPointDtos);
        dto.setInstanceId(entity.getInstance().getId());
        dto.setName(entity.getName());
        dto.setResultsCount(resultRepository.countByCheckPointLeg(entity));
        dto.setType(Type.fromInt(entity.getType()));
        dto.setProfile(entity.getProfile());

        return dto;
    }

    @Required
    public void setCheckPointAssembler(CheckPointAssembler checkPointAssembler) {
        this.checkPointAssembler = checkPointAssembler;
    }

    @Required
    public void setInstanceRepository(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @Required
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
}
