package com.piro.run.dao;

import com.piro.run.entity.ParticipantRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ppirovski on 6/12/15. In Code we trust
 */
public interface ParticipantRequestRepository extends JpaRepository<ParticipantRequest, Long> {
}
