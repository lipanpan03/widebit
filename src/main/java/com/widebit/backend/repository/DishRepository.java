package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<ProjectDishEntity,Integer> {

    List<ProjectDishEntity> findAllByDrawerId(int drawerId);

}
