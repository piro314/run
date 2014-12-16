package com.piro.run.service.impl;

import com.piro.run.assembler.CompetitionAssembler;

import com.piro.run.dao.CompetitionRepository;
import com.piro.run.dto.CompetitionDto;
import com.piro.run.entity.Competition;
import com.piro.run.service.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CompetitionServiceImpl implements CompetitionService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private CompetitionRepository competitionRepository;

    private CompetitionAssembler competitionAssembler;


    @Override
    @Transactional(readOnly = true)
    public List<CompetitionDto> getAllCompetitions() {

        LOG.debug("Listing all competitions");
        List<Competition> entities = competitionRepository.findAll();

        return competitionAssembler.toDtos(entities);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void update(CompetitionDto toUpdate) {
        Competition entity = competitionAssembler.toEntity(toUpdate);
        LOG.debug("Updating competition with id = "+toUpdate.getId());
        competitionRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public CompetitionDto createNew(CompetitionDto dto) {
        LOG.debug("Creating new Competition");
        Competition entity = competitionAssembler.toEntity(dto);
        entity = competitionRepository.save(entity);
        return competitionAssembler.toDto(entity);
    }

    @Required
    public void setCompetitionAssembler(CompetitionAssembler competitionAssembler) {
        this.competitionAssembler = competitionAssembler;
    }

    @Required
    public void setCompetitionRepository(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

}
