package com.piro.run.dao;

import com.piro.run.dto.statistics.RecordsDto;
import com.piro.run.dto.statistics.UserResultDto;
import com.piro.run.entity.FinalResult;
import com.piro.run.entity.Leg;
import com.piro.run.entity.Result;
import com.piro.run.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByCheckPointLegOrderByParticipantAscCheckPointDistanceFromStartAsc(Leg leg);

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
                    "group by c.id, i.id, l.id, p.male, p.category")
    List<Object[]> getChampionsData();

    @Query( value = "select new com.piro.run.dto.statistics.RecordsDto(fr.cName, fr.iName, fr.lName, fr.pName, fr.distance, min(fr.time)) " +
                    "from FinalResult  fr " +
                    "where fr.male = :male " +
                    "and fr.type = :type " +
                    "group by fr.cId, fr.iId, fr.lId " +
                    "order by fr.cName,fr.lName,fr.iName "
            )
    List<RecordsDto> getRecords(@Param("male") boolean male, @Param("type") int type);

    @Query( value = "select new com.piro.run.dto.statistics.UserResultDto(p.username, c.name, i.name, l.name, r.time, i.startDate) " +
            "from Result r " +
            "join r.checkPoint as cp " +
            "join r.participant as p " +
            "join cp.leg l " +
            "join l.instance as i " +
            "join i.competition as c " +
            "where p.username = :username " +
            "and cp.last = true " +
            "and r.time > 0 " +
            "order by i.startDate desc "
    )
    List<UserResultDto> getUserResults(@Param("username") String username);

    @Query( value = "from FinalResult fr " +
                    "where fr.time in (:times) "
            )
    List<FinalResult> getFinalResultsByTime(@Param("times") List<Long> times);

}
