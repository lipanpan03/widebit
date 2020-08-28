package com.widebit.backend.repository;

import com.widebit.backend.entity.ProjectSampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SampleRepository extends JpaRepository<ProjectSampleEntity,Integer> {

    List<ProjectSampleEntity> findAllByInfoSheetId(String infoSheetId);

    List<ProjectSampleEntity> findAllByCheckoutSheetId(Integer checkoutSheetId);

    List<ProjectSampleEntity> findAllByFatherSampleId(int fatherSampleId);
}
