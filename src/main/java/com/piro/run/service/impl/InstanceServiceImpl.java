package com.piro.run.service.impl;

import com.piro.run.assembler.CompetitionAssembler;
import com.piro.run.assembler.InstanceAssembler;
import com.piro.run.assembler.impl.CompetitionAssemblerImpl;
import com.piro.run.assembler.impl.InstanceAssemblerImpl;
import com.piro.run.dao.InstanceRepository;
import com.piro.run.dto.CompetitionDto;
import com.piro.run.dto.InstanceDto;
import com.piro.run.entity.Competition;
import com.piro.run.entity.Instance;
import com.piro.run.service.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ppirovski on 12/18/14. In Code we trust
 */
public class InstanceServiceImpl implements InstanceService {

    private static final Logger LOG = LoggerFactory.getLogger(InstanceServiceImpl.class);

    private InstanceRepository instanceRepository;

    private InstanceAssembler instanceAssembler;
    private CompetitionAssembler competitionAssembler;

    @Override
    @Transactional
    public List<InstanceDto> listByCompetition(CompetitionDto competitionDto) {

        Competition competition = competitionAssembler.toEntity(competitionDto);
        List<Instance> entities = instanceRepository.findByCompetition(competition);

        return instanceAssembler.toDtos(entities);
    }

    @Required
    public void setInstanceAssembler(InstanceAssemblerImpl instanceAssembler) {
        this.instanceAssembler = instanceAssembler;
    }

    @Required
    public void setInstanceRepository(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @Required
    public void setCompetitionAssembler(CompetitionAssemblerImpl competitionAssembler) {
        this.competitionAssembler = competitionAssembler;
    }
}
