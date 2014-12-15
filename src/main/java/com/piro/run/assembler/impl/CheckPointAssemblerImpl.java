package com.piro.run.assembler.impl;

import com.piro.run.assembler.CheckPointAssembler;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.entity.CheckPoint;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CheckPointAssemblerImpl extends BaseAssembler<CheckPoint, CheckPointDto>
                                     implements CheckPointAssembler {
    @Override
    public CheckPoint toEntity(CheckPointDto dto) {
        CheckPoint entity = new CheckPoint();

        entity.setName(dto.getName());
        entity.setAltitude(dto.getAltitude());
        entity.setDistanceFromStart(dto.getDistanceFromStart());
        entity.setId(dto.getId());

        return entity;
    }

    @Override
    public CheckPointDto toDto(CheckPoint entity) {

        CheckPointDto dto = new CheckPointDto();

        dto.setId(entity.getId());
        dto.setAltitude(entity.getAltitude());
        dto.setDistanceFromStart(entity.getDistanceFromStart());
        dto.setName(entity.getName());

        return dto;
    }
}
