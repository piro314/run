package com.piro.run.assembler.impl;

import com.piro.run.assembler.InstanceAssembler;
import com.piro.run.assembler.LegAssembler;
import com.piro.run.dao.CompetitionRepository;
import com.piro.run.dto.InstanceDto;
import com.piro.run.entity.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class InstanceAssemblerImpl extends BaseAssembler<Instance, InstanceDto>
                                   implements InstanceAssembler {

    private static final Logger LOG = LoggerFactory.getLogger(InstanceAssemblerImpl.class);

    private LegAssembler legAssembler;

    private CompetitionRepository competitionRepository;

    @Override
    public Instance toEntity(InstanceDto dto) {
        LOG.debug("Transforming to entity dto with id = "+dto.getId());

        Instance entity = new Instance();

        entity.setName(dto.getName());
        entity.setId(dto.getId());
        entity.setEndDate(dto.getEndDate());
        entity.setStartDate(dto.getStartDate());
        entity.setLegs(legAssembler.toEntities(dto.getLegs()));
        entity.setCompetition(competitionRepository.findOne(dto.getCompetitionId()));

        return entity;
    }

    @Override
    public InstanceDto toDto(Instance entity) {
        LOG.debug("Transforming to dto entity with id = "+entity.getId());

        InstanceDto dto = new InstanceDto();

        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getStartDate());
        dto.setId(entity.getId());
        dto.setCompetitionId(entity.getCompetition().getId());
        dto.setLegs(legAssembler.toDtos(entity.getLegs()));
        dto.setName(entity.getName());

        return dto;
    }

    @Required
    public void setLegAssembler(LegAssemblerImpl legAssembler) {
        this.legAssembler = legAssembler;
    }

    @Required
    public void setCompetitionRepository(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }
}
