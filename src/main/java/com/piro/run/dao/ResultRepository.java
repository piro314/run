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

    @Query(nativeQuery = true,
            value = "select c.name as c_name, c.id , i.name as i_name, i.id ," +
                    " l.name as l_name, l.id , p.is_male , p.category ," +
                    " p.name as p_name, p.username , min(r.time), l.type  " +
                    "from competitions c " +
                    "join instances i on i.competition_id = c.id " +
                    "join legs l on l.instance_id = i.id " +
                    "join run.check_points cp on cp.leg_id = l.id " +
                    "join results r on r.check_point_id = cp.id  " +
                    "join participants p on r.participant_id = p.id " +
                    "where cp.is_last  " +
                    "and r.time > 0 " +
                    "group by c.id, i.id, l.id, p.is_male, p.category ")
    List<Object[]> getChampionsData();


}
