package com.piro.run.service;

import com.piro.run.dto.CompetitionDto;
import com.piro.run.entity.Competition;

import java.util.List;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public interface CompetitionService {

    List<CompetitionDto> getAllCompetitions();

    void update(CompetitionDto toUpdate);

    CompetitionDto createNew(CompetitionDto dto);

    void delete(Long competitionId);
}
