package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MaterialRepository extends JpaRepository<ProjectMaterialEntity,Integer> {

    List<ProjectMaterialEntity> findAllByDeleteRemark(int deleteRemark);
}
