package com.piro.run.service.impl;

import com.piro.run.assembler.CompetitionAssembler;

import com.piro.run.dao.CompetitionRepository;
import com.piro.run.dto.CompetitionDto;
import com.piro.run.entity.Competition;
import com.piro.run.service.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
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
        List<Competition> entities = competitionRepository.findAll();

        return competitionAssembler.toDtos(entities);
    }

    @Override
    public void update(CompetitionDto toUpdate) {
        Competition entity = competitionAssembler.toEntity(toUpdate);
        competitionRepository.saveAndFlush(entity);
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
