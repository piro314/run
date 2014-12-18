package com.piro.run.assembler.impl;

import com.piro.run.assembler.CompetitionAssembler;
import com.piro.run.assembler.InstanceAssembler;
import com.piro.run.dto.CompetitionDto;
import com.piro.run.entity.Competition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CompetitionAssemblerImpl extends BaseAssembler<Competition, CompetitionDto>
                                      implements CompetitionAssembler{

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionAssemblerImpl.class);

    private InstanceAssembler instanceAssembler;
    @Override
    public Competition toEntity(CompetitionDto dto) {
 //       LOG.debug("Transforming to entity dto with id = "+dto.getId());

        Competition entity = new Competition();

        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setInstances(instanceAssembler.toEntities(dto.getInstances()));
        entity.setImageUrl(dto.getImageUrl());

        return entity;
    }

    @Override
    public CompetitionDto toDto(Competition entity) {
     //   LOG.debug("Transforming to dto entity with id = "+entity.getId());

        CompetitionDto dto = new CompetitionDto();

        dto.setUrl(entity.getUrl());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setId(entity.getId());
        dto.setInstances(instanceAssembler.toDtos(entity.getInstances()));
        dto.setImageUrl(entity.getImageUrl());

        return dto;
    }


    @Required
    public void setInstanceAssembler(InstanceAssembler instanceAssembler) {
        this.instanceAssembler = instanceAssembler;
    }
}
