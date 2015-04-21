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

    List<Result> findByCheckPointLegOrderByParticipantAsc(Leg leg);
}
