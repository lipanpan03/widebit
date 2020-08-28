package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectFridgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<ProjectFridgeEntity,Integer> {

    ProjectFridgeEntity findByFridgeId(String fridgeId);
}
