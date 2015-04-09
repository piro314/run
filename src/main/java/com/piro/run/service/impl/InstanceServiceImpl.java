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
import org.springframework.security.access.annotation.Secured;
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
    @Transactional(readOnly = true)
    public List<InstanceDto> listByCompetition(CompetitionDto competitionDto) {

        Competition competition = competitionAssembler.toEntity(competitionDto);
        List<Instance> entities = instanceRepository.findByCompetition(competition);

        return instanceAssembler.toDtos(entities);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void update(InstanceDto toUpdate) {
        Instance instance = instanceAssembler.toEntity(toUpdate);
        LOG.debug("updating instance with id= "+toUpdate.getId());
        instanceRepository.save(instance);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public InstanceDto createNew(InstanceDto dto) {
        Instance instance = instanceAssembler.toEntity(dto);
        LOG.debug("creating new instance of competition with id= "+dto.getCompetitionId());
        instance = instanceRepository.save(instance);
        return instanceAssembler.toDto(instance);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void delete(Long instanceId) {
        LOG.debug("deleting instance with id= "+instanceId);
        instanceRepository.delete(instanceId);
    }

    @Override
    @Transactional(readOnly = true)
    public InstanceDto getById(Long id) {
        Instance instance = instanceRepository.findOne(id);
        return instanceAssembler.toDto(instance);

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
