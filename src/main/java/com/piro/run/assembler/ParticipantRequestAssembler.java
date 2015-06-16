package com.piro.run.assembler;

import com.piro.run.dto.ParticipantRequestDto;
import com.piro.run.entity.ParticipantRequest;

/**
 * Created by ppirovski on 6/12/15. In Code we trust
 */
public interface ParticipantRequestAssembler extends Assembler<ParticipantRequest, ParticipantRequestDto> {

    @Override
    ParticipantRequest toEntity(ParticipantRequestDto dto);

    @Override
    ParticipantRequestDto toDto(ParticipantRequest entity);
}
