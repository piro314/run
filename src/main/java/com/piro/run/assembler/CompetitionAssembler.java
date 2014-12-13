package com.piro.run.assembler;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.entity.Competition;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public interface CompetitionAssembler {

    Competition toEntity(CompetitionDto dto);

    CompetitionDto toDto(Competition entity);
}
