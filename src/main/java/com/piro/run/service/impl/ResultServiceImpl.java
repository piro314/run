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

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantResultDto> getResultsByLegGroupByParticipant(LegDto legDto) {
        List<ResultDto> resultDtos = new ArrayList<>();
        Leg leg = legAssembler.toEntity(legDto);

        List<Result> results= resultRepository.findByCheckPointLegOrderByParticipantAsc(leg);
        return resultAssembler.toParticipantResultDto(results);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void update(ParticipantResultDto participantResultDto) {

    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void delete(ParticipantResultDto participantResultDto) {

    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void createNew(ParticipantResultDto participantResultDto) {
        Participant participant = new Participant();
        participant.setName(participantResultDto.getParticipantName());
        participant.setUsername(participantResultDto.getParticipantUsername());
        participantRepository.save(participant);

        List<Result> results = new ArrayList<>();
        for(ResultDto resultDto: participantResultDto.getResults()){
            Result result = new Result();
            result.setParticipant(participant);

            CheckPoint checkPoint = checkPointRepository.findOne(resultDto.getCheckPointId());
            result.setCheckPoint(checkPoint);

            result.setTime(TimeUtils.covertToMillis(resultDto.getHours(), resultDto.getMinutes(), resultDto.getSeconds()));

            results.add(result);
        }

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
