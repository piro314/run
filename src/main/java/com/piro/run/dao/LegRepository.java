package com.piro.run.dao;

import com.piro.run.dto.statistics.LegStatisticsDto;
import com.piro.run.entity.Instance;
import com.piro.run.entity.Leg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public interface LegRepository extends JpaRepository<Leg, Long> {

    List<Leg> findByInstance(Instance instance);

    @Query(" select new com.piro.run.dto.statistics.LegStatisticsDto(l.id, l.name, l.distance, l.dPlus, l.dMinus, l.highest, l.lowest, " +
            "                                              l.instance.name, l.instance.competition.name, l.type) " +
            "from Leg l " +
            "order by l.distance desc "
    )
    List<LegStatisticsDto> getLegStatistics();


}
