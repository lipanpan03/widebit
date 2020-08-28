package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectProjectEntity,Integer> {

    ProjectProjectEntity findByProjectId(String projectId);
}
