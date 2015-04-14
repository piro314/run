package com.piro.run.service.impl;

import com.piro.run.assembler.InstanceAssembler;
import com.piro.run.assembler.LegAssembler;
import com.piro.run.assembler.impl.InstanceAssemblerImpl;
import com.piro.run.assembler.impl.LegAssemblerImpl;
import com.piro.run.dao.CheckPointRepository;
import com.piro.run.dao.LegRepository;
import com.piro.run.dto.InstanceDto;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Instance;
import com.piro.run.entity.Leg;
import com.piro.run.service.LegService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public class LegServiceImpl implements LegService {

    private LegAssembler legAssembler;
    private LegRepository legRepository;
    private InstanceAssembler instanceAssembler;
    private CheckPointRepository checkPointRepository;

    private static final Logger LOG = LoggerFactory.getLogger(LegServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public List<LegDto> listByInstance(InstanceDto instanceDto) {

        Instance instance = instanceAssembler.toEntity(instanceDto);
        List<Leg> legs = legRepository.findByInstance(instance);

        return legAssembler.toDtos(legs);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void update(LegDto legDto) {

        Leg leg = legAssembler.toEntity(legDto);
        LOG.debug("updating leg with id= "+legDto.getId());
        legRepository.save(leg);

    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public LegDto createNew(LegDto legDto) {

        Leg leg = legAssembler.toEntity(legDto);
        LOG.debug("creating  leg of instance with id= "+legDto.getInstanceId());
        leg = legRepository.save(leg);

        return legAssembler.toDto(leg);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void delete(Long id) {

        LOG.debug("deleting leg with id = " +id);
        legRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public LegDto getById(Long id) {

        Leg leg = legRepository.findOne(id);

        return legAssembler.toDto(leg);
    }

    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void cloneCheckPoints(Long fromLegId, Long toLegId) {
        if(fromLegId == null || toLegId == null){
            throw new IllegalArgumentException("cloneCheckPoints parameter is null");
        }
        if(fromLegId.longValue() == toLegId.longValue()){
            return;
        }
        Leg from = legRepository.findOne(fromLegId);
        Leg to = legRepository.findOne(toLegId);

        List<CheckPoint> fromCheckPoints = from.getCheckPoints();
        List<CheckPoint> toCheckPoints = to.getCheckPoints();
        toCheckPoints.clear();

        for(CheckPoint cp : fromCheckPoints){
            CheckPoint newCP = cloneCheckPoint(cp, to);
            toCheckPoints.add(newCP);
        }

    }

    private static CheckPoint cloneCheckPoint(CheckPoint original, Leg to){
        CheckPoint result = new CheckPoint();
        result.setLeg(to);
        result.setAltitude(original.getAltitude());
        result.setDistanceFromStart(original.getDistanceFromStart());
        result.setName(original.getName());

        return result;
    }

    //-------------------- Setters ------------------------

    public void setLegAssembler(LegAssembler legAssembler) {
        this.legAssembler = legAssembler;
    }


    public void setLegRepository(LegRepository legRepository) {
        this.legRepository = legRepository;
    }


    public void setInstanceAssembler(InstanceAssemblerImpl instanceAssembler) {
        this.instanceAssembler = instanceAssembler;
    }

    public void setCheckPointRepository(CheckPointRepository checkPointRepository) {
        this.checkPointRepository = checkPointRepository;
    }
}
