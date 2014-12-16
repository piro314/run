package com.piro.run.assembler.impl;

import com.piro.run.assembler.CheckPointAssembler;
import com.piro.run.assembler.LegAssembler;
import com.piro.run.dao.InstanceRepository;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.Leg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class LegAssemblerImpl extends BaseAssembler<Leg, LegDto>
                              implements LegAssembler {

    private static final Logger LOG = LoggerFactory.getLogger(LegAssemblerImpl.class);

    private CheckPointAssembler checkPointAssembler;

    @Resource
    private InstanceRepository instanceRepository;

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

        return entity;
    }

    @Override
    public LegDto toDto(Leg entity) {
        LOG.debug("Transforming to dto entity with id = "+entity.getId());

        LegDto dto = new LegDto();

        dto.setLowest(entity.getLowest());
        dto.setId(entity.getId());
        dto.setHighest(entity.getHighest());
        dto.setdPlus(entity.getdPlus());
        dto.setDistance(entity.getDistance());
        dto.setdMinus(dto.getdMinus());
        dto.setCheckPoints(checkPointAssembler.toDtos(entity.getCheckPoints()));
        dto.setInstanceId(entity.getInstance().getId());
        dto.setName(entity.getName());

        return dto;
    }

    @Required
    public void setCheckPointAssembler(CheckPointAssembler checkPointAssembler) {
        this.checkPointAssembler = checkPointAssembler;
    }

}
