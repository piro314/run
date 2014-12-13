package com.piro.run.assembler.impl;

import com.piro.run.assembler.CompetitionAssembler;
import com.piro.run.dto.CompetitionDto;
import com.piro.run.entity.Competition;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CompetitionAssemblerImpl implements CompetitionAssembler{
    @Override
    public Competition toEntity(CompetitionDto dto) {
        Competition entity = new Competition();

        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());


        return entity;
    }

    @Override
    public CompetitionDto toDto(Competition entity) {
        return null;
    }
}
