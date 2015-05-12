package com.piro.run.dao;

import com.piro.run.entity.Leg;
import com.piro.run.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByCheckPointLegOrderByParticipantAscCheckPointAsc(Leg leg);

    long countByCheckPointLeg(Leg leg);

    @Query( value = "select c.name as c_name, c.id as c_id, " +
                    " i.name as i_name, i.id as i_id, " +
                    " l.name as l_name, l.id as l_id, " +
                    " p.male as p_is_male, p.category as p_category, " +
                    " p.name as p_name, p.username as p_username, " +
                    " min(r.time) as time, l.type as l_type " +
                    "from Result r " +
                    "join r.checkPoint as cp " +
                    "join r.participant as p " +
                    "join cp.leg l " +
                    "join l.instance as i " +
                    "join i.competition as c " +
                    "where cp.last = true " +
                    "and r.time > 0 " +
                    "group by c.id, i.id, l.id, p.male, p.category, c.name, i.name, l.name, p.name, p.username, l.type ")
    List<Object[]> getChampionsData();


}
