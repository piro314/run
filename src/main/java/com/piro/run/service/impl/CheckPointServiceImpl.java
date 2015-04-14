package com.piro.run.service.impl;

import com.piro.run.assembler.CheckPointAssembler;
import com.piro.run.assembler.LegAssembler;
import com.piro.run.assembler.impl.CheckPointAssemblerImpl;
import com.piro.run.assembler.impl.LegAssemblerImpl;
import com.piro.run.dao.CheckPointRepository;
import com.piro.run.dao.LegRepository;
import com.piro.run.dto.CheckPointDto;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Leg;
import com.piro.run.service.CheckPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public class CheckPointServiceImpl implements CheckPointService {

    private static final Logger LOG = LoggerFactory.getLogger(CheckPointServiceImpl.class);

    private CheckPointRepository checkPointRepository;
    private CheckPointAssembler checkPointAssembler;
    private LegAssembler legAssembler;

    @Override
    @Transactional(readOnly = true)
    public List<CheckPointDto> getByLeg(LegDto legDto) {
        Leg leg = legAssembler.toEntity(legDto);
        List<CheckPoint> checkPoints= checkPointRepository.findByLeg(leg);

        return checkPointAssembler.toDtos(checkPoints);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void update(CheckPointDto checkPointDto) {
        CheckPoint checkPoint = checkPointAssembler.toEntity(checkPointDto);
        LOG.debug("updating checkPoint with id = "+ checkPoint.getId());

        checkPointRepository.save(checkPoint);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public CheckPointDto createNew(CheckPointDto checkPointDto) {
        CheckPoint checkPoint = checkPointAssembler.toEntity(checkPointDto);
        LOG.debug("creating new checkPoint for leg with id = "+checkPointDto.getLegId());
        checkPoint = checkPointRepository.save(checkPoint);

        return checkPointAssembler.toDto(checkPoint);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void delete(Long id) {
        LOG.debug("deleting checkPoint with id = "+id);
        checkPointRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CheckPointDto getById(Long id) {
        CheckPoint checkPoint = checkPointRepository.findOne(id);
        return checkPointAssembler.toDto(checkPoint);
    }


    @Required
    public void setCheckPointAssembler(CheckPointAssemblerImpl checkPointAssembler) {
        this.checkPointAssembler = checkPointAssembler;
    }

    @Required
    public void setLegAssembler(LegAssemblerImpl legAssembler) {
        this.legAssembler = legAssembler;
    }

    @Required
    public void setCheckPointRepository(CheckPointRepository checkPointRepository) {
        this.checkPointRepository = checkPointRepository;
    }
}
