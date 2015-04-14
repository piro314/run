package com.piro.run.service;

import com.piro.run.dto.InstanceDto;
import com.piro.run.dto.LegDto;

import java.util.List;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public interface LegService {

    List<LegDto> listByInstance(InstanceDto instanceDto);

    void update(LegDto legDto);

    LegDto createNew(LegDto legDto);

    void delete(Long id);

    LegDto getById(Long id);

    void cloneCheckPoints(Long fromLegId, Long toLegId);


}
