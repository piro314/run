package com.piro.run.assembler;

import com.piro.run.dto.CheckPointDto;
import com.piro.run.entity.CheckPoint;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public interface CheckPointAssembler extends Assembler<CheckPoint, CheckPointDto>{

    CheckPoint toEntity(CheckPointDto dto);

    CheckPointDto toDto(CheckPoint entity);
}
