package com.piro.run.assembler.impl;

import com.piro.run.assembler.CheckPointAssembler;
import com.piro.run.dao.LegRepository;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Leg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CheckPointAssemblerImpl extends BaseAssembler<CheckPoint, CheckPointDto>
                                     implements CheckPointAssembler {

    private static final Logger LOG = LoggerFactory.getLogger(CheckPointAssemblerImpl.class);

    private LegRepository legRepository;

    @Override
    public CheckPoint toEntity(CheckPointDto dto) {
        LOG.debug("Transforming to entity dto with id = "+dto.getId());

        CheckPoint entity = new CheckPoint();

        entity.setName(dto.getName());
        entity.setAltitude(dto.getAltitude());
        entity.setDistanceFromStart(dto.getDistanceFromStart());
        entity.setId(dto.getId());
        Leg leg = legRepository.findOne(dto.getLegId());
        entity.setLeg(leg);
        entity.setLast(dto.isLast());

        return entity;
    }

    @Override
    public CheckPointDto toDto(CheckPoint entity) {
        if(entity == null){
            return null;
        }
        LOG.debug("Transforming to dto entity with id = "+entity.getId());

        CheckPointDto dto = new CheckPointDto();

        dto.setId(entity.getId());
        dto.setAltitude(entity.getAltitude());
        dto.setDistanceFromStart(entity.getDistanceFromStart());
        dto.setName(entity.getName());
        dto.setLegId(entity.getLeg().getId());
        dto.setLast(entity.isLast());

        return dto;
    }


    public void setLegRepository(LegRepository legRepository) {
        this.legRepository = legRepository;
    }
}
