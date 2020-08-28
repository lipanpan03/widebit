package com.widebit.backend.service;

import com.widebit.backend.entity.ProjectLabEntity;
import com.widebit.backend.entity.ProjectProjectEntity;
import com.widebit.backend.entity.ProjectSampleEntity;
import com.widebit.backend.repository.LabRepository;
import com.widebit.backend.repository.ProjectRepository;
import com.widebit.backend.repository.SampleRepository;
import com.widebit.backend.repository.SampleinfosheetRepository;
import com.widebit.backend.util.EAN13Generation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService implements ILibraryService {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SampleinfosheetRepository sampleinfosheetRepository;

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public JSONObject errorResult(String error,String errorDescription){
        JSONObject result = new JSONObject();
        result.put("data", new JSONObject());
        result.put("code", error);
        JSONArray error_description = new JSONArray();
        error_description.add(errorDescription);
        result.put("error_description", error_description);
        return result;
    }

    @Override
    public JSONObject getProjectInformation(int id) {
        Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
        if (!sampleEntityOptional.isPresent()) return errorResult("INTERVAL_ERROR","样品不存在");
        ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
        ProjectLabEntity labEntity = labRepository.findByLabId(sampleEntity.getLabId());
        if (labEntity==null) return errorResult("INTERVAL_ERROR","项目（实验）不存在");
        ProjectProjectEntity projectEntity = projectRepository.findByProjectId(labEntity.getProjectId());
        if (projectEntity==null) return errorResult("INTERVAL_ERROR","合同（项目）不存在");
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("sample_id", EAN13Generation.barcodeValidity("0001" + String.format("%08d", sampleEntity.getId())));
        item.put("project_name",projectEntity.getName());
        item.put("project_id",projectEntity.getProjectId());
        item.put("lab_name",labEntity.getName());
        item.put("lab_id",labEntity.getLabId());
        item.put("sample_name",sampleEntity.getName());
        item.put("sample_type",sampleEntity.getSpec());
        array.add(item);
        result.put("data",array);
        return result;
    }
}
