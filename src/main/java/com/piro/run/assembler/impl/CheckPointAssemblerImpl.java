package com.piro.run.assembler.impl;

import com.piro.run.assembler.CheckPointAssembler;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.entity.CheckPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CheckPointAssemblerImpl extends BaseAssembler<CheckPoint, CheckPointDto>
                                     implements CheckPointAssembler {

    private static final Logger LOG = LoggerFactory.getLogger(CheckPointAssemblerImpl.class);

    @Override
    public CheckPoint toEntity(CheckPointDto dto) {
        LOG.debug("Transforming to entity dto with id = "+dto.getId());

        CheckPoint entity = new CheckPoint();

        entity.setName(dto.getName());
        entity.setAltitude(dto.getAltitude());
        entity.setDistanceFromStart(dto.getDistanceFromStart());
        entity.setId(dto.getId());

        return entity;
    }

    @Override
    public CheckPointDto toDto(CheckPoint entity) {
        LOG.debug("Transforming to dto entity with id = "+entity.getId());

        CheckPointDto dto = new CheckPointDto();

        dto.setId(entity.getId());
        dto.setAltitude(entity.getAltitude());
        dto.setDistanceFromStart(entity.getDistanceFromStart());
        dto.setName(entity.getName());

        return dto;
    }
}
