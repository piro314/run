package com.piro.run.dao;

import com.piro.run.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ppirovski on 12/13/14. In Code we trust
 */
public interface CompetitionRepository extends JpaRepository<User, Long> {
}
