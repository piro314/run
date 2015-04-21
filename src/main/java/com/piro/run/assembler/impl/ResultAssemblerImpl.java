package com.piro.run.assembler.impl;

import com.piro.run.assembler.ResultAssembler;
import com.piro.run.dao.CheckPointRepository;
import com.piro.run.dao.ParticipantRepository;
import com.piro.run.dto.ParticipantResultDto;
import com.piro.run.dto.ResultDto;
import com.piro.run.entity.Participant;
import com.piro.run.entity.Result;
import com.piro.run.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public class ResultAssemblerImpl extends BaseAssembler<Result, ResultDto> implements ResultAssembler {

    private CheckPointRepository checkPointRepository;
    private ParticipantRepository participantRepository;

    @Override
    public Result toEntity(ResultDto dto) {
        Result entity = new Result();

        entity.setCheckPoint(checkPointRepository.findOne(dto.getCheckPointId()));
        entity.setId(dto.getId());
        entity.setParticipant(participantRepository.findOne(dto.getParticipantId()));

        Long time = dto.getTime();
        if(time == null) {
            time = (60*60*dto.getHours() + 60*dto.getMinutes() + dto.getSeconds())*1000L;
        }
        entity.setTime(time);

        return entity;
    }

    @Override
    public ResultDto toDto(Result entity) {
        ResultDto dto = new ResultDto();

        long time = entity.getTime();
        dto.setTime(time);
        dto.setHours(TimeUtils.getHours(time));
        dto.setMinutes(TimeUtils.getMinutes(time));
        dto.setSeconds(TimeUtils.getSeconds(time));

        dto.setId(entity.getId());
        dto.setCheckPointId(entity.getCheckPoint().getId());
        dto.setCheckPointName(entity.getCheckPoint().getName());
        dto.setDistanceFromStart(entity.getCheckPoint().getDistanceFromStart());
        Participant participant = entity.getParticipant();
        dto.setParticipantId(participant.getId());

        return dto;
    }

    @Override
    public List<ParticipantResultDto> toParticipantResultDto(List<Result> results) {
        List<ParticipantResultDto> list = new ArrayList<>();

        if(results == null || results.isEmpty()){
            return new ArrayList<>();
        }
        Long currentParticipantId = -1L;

        ParticipantResultDto participantResultDto = new ParticipantResultDto();
        for (Result r : results) {
            if(currentParticipantId.longValue() != r.getParticipant().getId().longValue()){
                list.add(participantResultDto);

                participantResultDto = new ParticipantResultDto();
                participantResultDto.setParticipantUsername(r.getParticipant().getUsername());
                participantResultDto.setParticipantName(r.getParticipant().getName());
                participantResultDto.setParticipantId(r.getParticipant().getId());
                participantResultDto.setResults(new ArrayList<ResultDto>());
                currentParticipantId = r.getParticipant().getId();
            }
            participantResultDto.getResults().add(this.toDto(r));

        }

        if(!list.contains(participantResultDto)){
            list.add(participantResultDto);
        }

        return list;
    }

    @Required
    public void setParticipantRepository(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Required
    public void setCheckPointRepository(CheckPointRepository checkPointRepository) {
        this.checkPointRepository=checkPointRepository;
    }
}
