package com.piro.run.assembler;

import com.piro.run.dto.ParticipantResultDto;
import com.piro.run.dto.ResultDto;
import com.piro.run.entity.Participant;
import com.piro.run.entity.Result;

import java.util.List;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public interface ResultAssembler extends Assembler<Result, ResultDto> {

    /**
     * requires parameter resultDtos list to be sorted by Participants
     * @param resultDtos
     * @return
     */
    List<ParticipantResultDto> toParticipantResultDto(List<Result> resultDtos);
}
