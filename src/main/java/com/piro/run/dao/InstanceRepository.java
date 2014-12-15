package com.piro.run.dao;

import com.piro.run.entity.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ppirovski on 12/15/14. In Code we trust
 */
public interface InstanceRepository extends JpaRepository<Instance, Long> {
}
