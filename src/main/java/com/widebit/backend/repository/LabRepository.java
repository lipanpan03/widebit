package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectLabEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabRepository extends JpaRepository<ProjectLabEntity,Integer> {

    ProjectLabEntity findByLabId(String labId);
}
