package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectPurchaselistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseListRepository extends JpaRepository<ProjectPurchaselistEntity,Integer> {

    List<ProjectPurchaselistEntity> findAllBySheetId(int sheetId);
}
