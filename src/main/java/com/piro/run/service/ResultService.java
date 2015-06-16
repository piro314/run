package com.piro.run.service;

import com.piro.run.dto.LegDto;
import com.piro.run.dto.ParticipantResultDto;
import com.piro.run.dto.ResultDto;

import java.util.List;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public interface ResultService {

    List<ParticipantResultDto> getResultsByLegGroupByParticipant(LegDto legDto);

    void update(ParticipantResultDto participantResultDto);

    void delete(ParticipantResultDto participantResultDto);

    void createNew(ParticipantResultDto participantResultDto);

    void linkParticipant(ParticipantResultDto participantResultDto, String username);
}
