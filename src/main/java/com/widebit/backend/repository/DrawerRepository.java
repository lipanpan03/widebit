package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectDrawerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawerRepository extends JpaRepository<ProjectDrawerEntity,Integer> {

    List<ProjectDrawerEntity> findAllByFridgeId(int fridgeId);

    ProjectDrawerEntity findByFridgeIdAndDrawerOrder(int fridgeId, int drawerOrder);
}
