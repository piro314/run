package com.piro.run.dao;

import com.piro.run.entity.CheckPoint;
import com.piro.run.entity.Leg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {

    List<CheckPoint> findByLeg(Leg leg);
}
