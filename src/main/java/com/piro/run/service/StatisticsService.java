package com.piro.run.service;

import com.piro.run.dto.statistics.LegStatisticsDto;
import com.piro.run.dto.statistics.RecordsDto;
import com.piro.run.dto.statistics.UserResultDto;
import com.piro.run.enums.Type;

import java.util.List;

/**
 * Created by ppirovski on 5/12/15. In Code we trust
 */
public interface StatisticsService {

    List<LegStatisticsDto> getLegStatistics();

    List<RecordsDto> getRecords(boolean male, Type type);

    List<UserResultDto> getUserResults(String username);
}
