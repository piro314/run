package com.piro.run.assembler;

import com.piro.run.dto.LegDto;
import com.piro.run.entity.Leg;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public interface LegAssembler extends Assembler<Leg, LegDto>{

    Leg toEntity(LegDto dto);

    LegDto toDto(Leg entity);
}
