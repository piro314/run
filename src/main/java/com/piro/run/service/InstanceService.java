package com.piro.run.service;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.dto.InstanceDto;

import java.util.List;

/**
 * Created by ppirovski on 12/18/14. In Code we trust
 */
public interface InstanceService {

    List<InstanceDto> listByCompetition(CompetitionDto competitionDto);
}
