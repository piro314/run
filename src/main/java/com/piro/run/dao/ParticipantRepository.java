package com.piro.run.dao;

import com.piro.run.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ppirovski on 4/15/15. In Code we trust
 */
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
