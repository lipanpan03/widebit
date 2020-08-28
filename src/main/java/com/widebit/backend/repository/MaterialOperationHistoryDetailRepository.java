package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectMaterialoperationhistorydetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialOperationHistoryDetailRepository extends JpaRepository<ProjectMaterialoperationhistorydetailEntity,Integer> {

    List<ProjectMaterialoperationhistorydetailEntity> findAllByOperationId(int operationId);
}
