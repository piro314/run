package com.piro.run.service.impl;

import com.piro.run.assembler.LegAssembler;
import com.piro.run.assembler.ResultAssembler;
import com.piro.run.dao.CheckPointRepository;
import com.piro.run.dao.LegRepository;
import com.piro.run.dao.ParticipantRepository;
import com.piro.run.dao.ResultRepository;
import com.piro.run.dto.LegDto;
import com.piro.run.dto.ParticipantResultDto;
import com.piro.run.dto.ResultDto;
import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Leg;
import com.piro.run.entity.Participant;
import com.piro.run.entity.Result;
import com.piro.run.service.ResultService;
import com.piro.run.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public class ResultServiceImpl implements ResultService {

    private ResultRepository resultRepository;
    private ResultAssembler resultAssembler;
    private LegAssembler legAssembler;
    private CheckPointRepository checkPointRepository;
    private LegRepository legRepository;
    private ParticipantRepository participantRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantResultDto> getResultsByLegGroupByParticipant(LegDto legDto) {
        List<ResultDto> resultDtos = new ArrayList<>();
        Leg leg = legAssembler.toEntity(legDto);

        List<Result> results= resultRepository.findByCheckPointLegOrderByParticipantAscCheckPointAsc(leg);
        return resultAssembler.toParticipantResultDto(results);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void update(ParticipantResultDto participantResultDto) {

        LOG.debug("updating results for participant with id = " +participantResultDto.getParticipantId() +
                " and leg id = "+participantResultDto.getLegId());

        List<Result> results = resultAssembler.toEntities(participantResultDto.getResults());
        resultRepository.save(results);

        Participant participant = participantRepository.findOne(participantResultDto.getParticipantId());
        participant.setUsername(participantResultDto.getParticipantUsername());
        participant.setName(participantResultDto.getParticipantName());
        participant.setNumber(participantResultDto.getParticipantNumber());
        participant.setCategory(participantResultDto.getCategory());
        participant.setMale(participantResultDto.isMale());
        participantRepository.save(participant);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void delete(ParticipantResultDto participantResultDto) {

        LOG.debug("deleting results for participant with id = " +participantResultDto.getParticipantId() +
                " and leg id = "+participantResultDto.getLegId());

        List<Result> results = resultAssembler.toEntities(participantResultDto.getResults());
        resultRepository.delete(results);
        participantRepository.delete(participantResultDto.getParticipantId());
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void createNew(ParticipantResultDto participantResultDto) {

        LOG.debug("creating participant with name = " + participantResultDto.getParticipantName());

        Participant participant = new Participant();
        participant.setName(participantResultDto.getParticipantName());
        participant.setUsername(participantResultDto.getParticipantUsername());
        participant.setNumber(participantResultDto.getParticipantNumber());
        participant.setCategory(participantResultDto.getCategory());
        participant.setMale(participantResultDto.isMale());
        participantRepository.save(participant);

        List<Result> results = new ArrayList<>();
        Long legId = null;
        for(ResultDto resultDto: participantResultDto.getResults()){
            Result result = new Result();
            result.setParticipant(participant);

            CheckPoint checkPoint = checkPointRepository.findOne(resultDto.getCheckPointId());
            result.setCheckPoint(checkPoint);
            legId = checkPoint.getLeg().getId();
            result.setTime(TimeUtils.covertToMillis(resultDto.getHours(), resultDto.getMinutes(), resultDto.getSeconds()));

            results.add(result);
        }

        LOG.debug("creating results for participant with name = " + participantResultDto.getParticipantName()+
                " and leg id = " + legId);

        results = resultRepository.save(results);

    }

    @Required
    public void setResultAssembler(ResultAssembler resultAssembler) {
        this.resultAssembler = resultAssembler;
    }

    @Required
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Required
    public void setLegAssembler(LegAssembler legAssembler) {
        this.legAssembler = legAssembler;
    }

    @Required
    public void setCheckPointRepository(CheckPointRepository checkPointRepository) {
        this.checkPointRepository = checkPointRepository;
    }

    @Required
    public void setLegRepository(LegRepository legRepository) {
        this.legRepository = legRepository;
    }

    @Required
    public void setParticipantRepository(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }
}
