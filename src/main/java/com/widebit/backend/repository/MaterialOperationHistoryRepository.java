package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectMaterialoperationhistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface MaterialOperationHistoryRepository extends JpaRepository<ProjectMaterialoperationhistoryEntity,Integer> {

    List<ProjectMaterialoperationhistoryEntity> findAllByOperateTimeBetweenOrderByOperateTimeDesc(Timestamp startTime, Timestamp endTime);

    List<ProjectMaterialoperationhistoryEntity> findAllByTypeAndOperateTimeBetweenOrderByOperateTimeDesc(String type, Timestamp startTime, Timestamp endTime);
}
