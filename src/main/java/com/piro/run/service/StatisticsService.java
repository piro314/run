package com.piro.run.service;

import com.piro.run.dto.LegStatisticsDto;

import java.util.List;

/**
 * Created by ppirovski on 5/12/15. In Code we trust
 */
public interface StatisticsService {

    List<LegStatisticsDto> getLegStatistics();
}
