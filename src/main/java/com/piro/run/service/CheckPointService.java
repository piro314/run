package com.piro.run.service;

import com.piro.run.dto.CheckPointDto;
import com.piro.run.dto.LegDto;
import com.piro.run.entity.CheckPoint;

import java.util.List;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public interface CheckPointService {

    List<CheckPointDto> getByLeg(LegDto legdto);

    void update(CheckPointDto checkPointDto);

    CheckPointDto createNew(CheckPointDto checkPointDto);

    void delete(Long id);

    CheckPointDto getById(Long id);
}
