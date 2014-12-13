package com.piro.run.service.impl;

import com.piro.run.dao.CompetitionRepository;
import com.piro.run.service.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public class CompetitionServiceImpl implements CompetitionService {

    private static final Logger LOG = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    @Resource
    private CompetitionRepository competitionRepository;


}
