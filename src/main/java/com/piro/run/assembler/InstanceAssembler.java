package com.piro.run.assembler;

import com.piro.run.dto.InstanceDto;
import com.piro.run.entity.Instance;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public interface InstanceAssembler extends Assembler<Instance, InstanceDto>{

    Instance toEntity(InstanceDto dto);

    InstanceDto toDto(Instance entitiy);
}
