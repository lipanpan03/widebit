package com.widebit.backend.service;

import com.widebit.backend.entity.*;
import com.widebit.backend.repository.*;
import com.widebit.backend.util.EAN13Generation;
import com.widebit.backend.util.Html2Pdf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SampleService implements ISampleService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SampleinfosheetRepository sampleinfosheetRepository;

    @Autowired
    private SampleRepository sampleRepository;

    private JSONObject generateData(ProjectSampleEntity sampleEntity, ProjectProjectEntity projectEntity, ProjectSampleinfosheetEntity sampleinfosheetEntity, double urgent) {
        JSONObject item = new JSONObject();
        item.put("id", sampleEntity.getId());
        item.put("urgent", urgent);
        item.put("sample_name", sampleEntity.getName());
        item.put("project_name", projectEntity.getName());
        item.put("storage_status", sampleEntity.getStorageStatus());
        if (sampleinfosheetEntity!=null)
            item.put("need_return", sampleinfosheetEntity.getNeedReturn());
        else
            item.put("need_return","");
        item.put("storage_location", sampleEntity.getStorageLocation());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ssssss");
        String date = format.format(sampleEntity.getArrivedTime().getTime());
        if (date.startsWith("1970"))
            item.put("arrived_time", "未到样");
        else
            item.put("arrived_time", date.substring(0, 10));
        if (sampleinfosheetEntity != null)
            item.put("data_size", sampleinfosheetEntity.getDataSize());
        else
            item.put("data_size", "");
        item.put("spec", sampleEntity.getSpec());
        item.put("status", sampleEntity.getStatus());
        item.put("image_note", sampleEntity.getImageNote());
        item.put("note", sampleEntity.getNote());
        return item;
    }

    private double getUrgent(ProjectLabEntity labEntity, ProjectProjectEntity projectEntity) {
        double urgent;
        if ("项目完结".equals(labEntity.getStatus())) urgent = 0.0;
        else {
            long now = System.currentTimeMillis(), launchTime = labEntity.getLaunchTime().getTime();
            urgent = (now - launchTime) * 1.0 / 86400000 / projectEntity.getProjectDuration();
            if (urgent > 1.0) urgent = 0.0;
        }
        return urgent;
    }

    public JSONObject findSamplesBySheetId(int sheetId) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        List<ProjectSampleEntity> sampleEntities = sampleRepository.findAllByInfoSheetId(String.valueOf(sheetId));
        if (sampleEntities.size() == 0) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        Optional<ProjectSampleinfosheetEntity> sampleinfosheetEntityOptional = sampleinfosheetRepository.findById(sheetId);
        if (!sampleinfosheetEntityOptional.isPresent()){
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectSampleinfosheetEntity sampleinfosheetEntity = sampleinfosheetEntityOptional.get();
        ProjectLabEntity labEntity = labRepository.findByLabId(sampleinfosheetEntity.getLabId());
        if (labEntity == null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectProjectEntity projectEntity = projectRepository.findByProjectId(labEntity.getProjectId());
        if (projectEntity == null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        double urgent = getUrgent(labEntity, projectEntity);
        for (ProjectSampleEntity sampleEntity : sampleEntities) {
            data.add(generateData(sampleEntity, projectEntity, sampleinfosheetEntity, urgent));
        }
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    public JSONObject notFoundData() {
        JSONObject result = new JSONObject();
        result.put("data", new JSONObject());
        result.put("code", "INTERNAL_ERROR");
        JSONArray errorDescription = new JSONArray();
        errorDescription.add("NOT FOUND");
        result.put("error_description", errorDescription);
        return result;
    }

    private JSONObject errorReturnData(String error){
        JSONObject result = new JSONObject();
        result.put("data", new JSONObject());
        result.put("code", "INTERNAL_ERROR");
        JSONArray errorDescription = new JSONArray();
        errorDescription.add(error);
        result.put("error_description", errorDescription);
        return result;
    }

    public JSONObject wrongFormat() {
        JSONObject result = new JSONObject();
        result.put("data", new JSONObject());
        result.put("code", "PARAMETER_ERROR");
        JSONArray errorDescription = new JSONArray();
        errorDescription.add("WRONG FORMAT");
        result.put("error_description", errorDescription);
        return result;
    }

    public JSONObject findSamplesBySampleId(int sampleId) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(sampleId);
        if (!sampleEntityOptional.isPresent()) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
        ProjectLabEntity labEntity = labRepository.findByLabId(sampleEntity.getLabId());
        if (labEntity == null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectProjectEntity projectEntity = projectRepository.findByProjectId(labEntity.getProjectId());
        if (projectEntity == null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        double urgent = getUrgent(labEntity, projectEntity);
        data.add(generateData(sampleEntity, projectEntity, null, urgent));
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    public JSONObject findAllSample() {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        List<ProjectSampleEntity> sampleEntityList = sampleRepository.findAll();
        for (ProjectSampleEntity sampleEntity:sampleEntityList){
            ProjectLabEntity labEntity = labRepository.findByLabId(sampleEntity.getLabId());
            if (labEntity == null) continue;
            ProjectProjectEntity projectEntity = projectRepository.findByProjectId(labEntity.getProjectId());
            if (projectEntity == null) continue;
            double urgent = getUrgent(labEntity, projectEntity);
            if ("已销毁".equals(sampleEntity.getStorageStatus()) || "已返样".equals(sampleEntity.getStorageStatus()))
                continue;
            if ("".equals(sampleEntity.getInfoSheetId()))
                data.add(generateData(sampleEntity, projectEntity, null, urgent));
            else {
                Optional<ProjectSampleinfosheetEntity> sampleinfosheetEntityOptional = sampleinfosheetRepository.findById(Integer.parseInt(sampleEntity.getInfoSheetId()));
                if (sampleinfosheetEntityOptional.isPresent())
                    data.add(generateData(sampleEntity, projectEntity, sampleinfosheetEntityOptional.get(), urgent));
                else
                    data.add(generateData(sampleEntity, projectEntity, null, urgent));
            }
        }
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    @Override
    public JSONObject saveSampleNote(int id, String status, String note, String imageNode) {
        Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
        if (!sampleEntityOptional.isPresent()) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
        sampleEntity.setId(id);
        sampleEntity.setStatus(status);
        sampleEntity.setNote(note);
        sampleEntity.setImageNote(imageNode);
        sampleRepository.save(sampleEntity);
        JSONObject result = new JSONObject();
        result.put("code", "SUCCESS");
        JSONArray error = new JSONArray();
        error.add("");
        result.put("error_description", error);
        return result;
    }

    private String generateLocation() {
        Random random = new Random();
        return "(" + random.nextInt(100) + "," + random.nextInt(100) + ")-(" + random.nextInt(100) + "," + random.nextInt(100) + "," + random.nextInt(100) + ")";
    }

    @Override
    public JSONObject prepareStoreIn(JSONArray samples) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        for (Object o : samples) {
            int id = Integer.parseInt((String) o);
            Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
            if (!sampleEntityOptional.isPresent()) continue;
            //存储sampleEntity
            ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
            sampleEntity.setId(id);
            if (sampleEntity.getStorageLocation() == null || "".equals(sampleEntity.getStorageLocation().trim()))
                sampleEntity.setStorageLocation(generateLocation());
            sampleRepository.save(sampleEntity);
            //修改前端返回存储位置和到达时间
            JSONObject item = findSamplesBySampleId(id);
            JSONObject itemData = (JSONObject) ((JSONArray) item.get("data")).get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ssssss");
            String dataString = format.format(new Date(System.currentTimeMillis()));
            itemData.put("storage_location", sampleEntity.getStorageLocation());
            itemData.put("arrived_time", dataString.substring(0, 10));
            data.add(itemData);
        }
        if (data.size() == 0) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    @Override
    public JSONObject storeIn(JSONArray samples) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        for (Object o : samples) {
            int id = Integer.parseInt((String) o);
            Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
            if (!sampleEntityOptional.isPresent()) continue;
            ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
            sampleEntity.setId(id);
            sampleEntity.setStorageStatus("已入库");
            sampleEntity.setArrivedTime(new Timestamp(System.currentTimeMillis()));
            sampleRepository.save(sampleEntity);
            JSONObject item = findSamplesBySampleId(id);
            JSONObject itemData = (JSONObject) ((JSONArray) item.get("data")).get(0);
            data.add(itemData);
        }
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    @Override
    public JSONObject storeInCancel(JSONArray samples) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        for (Object object : samples) {
            int id = Integer.parseInt((String) object);
            Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
            if (!sampleEntityOptional.isPresent()) continue;
            ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
            sampleEntity.setStorageLocation("");
            sampleRepository.save(sampleEntity);
            JSONObject item = findSamplesBySampleId(id);
            JSONObject itemData = (JSONObject) ((JSONArray) item.get("data")).get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ssssss");
            String date = format.format(sampleEntity.getArrivedTime().getTime());
            if (date.startsWith("1970"))
                itemData.put("arrived_time", "未到样");
            else
                itemData.put("arrived_time", date.substring(0, 10));
            itemData.put("storage_location", "");
            data.add(itemData);
        }
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    @Override
    public JSONObject storeOut(JSONArray samples, String action) throws IOException {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        JSONArray checkoutSheet = new JSONArray();
        int checkoutSheetId = 0;
        List<ProjectSampleEntity> sampleEntities = sampleRepository.findAll();
        for (ProjectSampleEntity sampleEntity : sampleEntities) {
            if (sampleEntity.getCheckoutSheetId() != null) {
                checkoutSheetId = Math.max(checkoutSheetId, sampleEntity.getCheckoutSheetId());
            }
        }
        checkoutSheetId++;
        for (Object o : samples) {
            int id = Integer.parseInt((String) o);
            Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
            if (!sampleEntityOptional.isPresent()) continue;
            ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
            String storageLocation = sampleEntity.getStorageLocation();
            if ("SAMPLE_OUT_DESTROY".equals(action)) {
                sampleEntity.setStorageLocation("");
                sampleEntity.setStorageStatus("已销毁");
                sampleEntity.setDestoryTime(new Timestamp(System.currentTimeMillis()));
            } else if ("SAMPLE_OUT_RETURN".equals(action)) {
                sampleEntity.setStorageLocation("");
                sampleEntity.setStorageStatus("已返样");
                sampleEntity.setReturnTime(new Timestamp(System.currentTimeMillis()));
            } else if ("SAMPLE_OUT_EXPERIMENT".equals(action)) {
                sampleEntity.setStorageStatus("已出库");
            }
            sampleEntity.setCheckoutSheetId(checkoutSheetId);
            sampleRepository.save(sampleEntity);
            JSONObject item = findSamplesBySampleId(id);
            JSONObject itemData = (JSONObject) ((JSONArray) item.get("data")).get(0);
            data.add(itemData);
            JSONObject checkoutItem = new JSONObject();
            checkoutItem.put("sample_name", sampleEntity.getName());
            checkoutItem.put("id", sampleEntity.getId());
            checkoutItem.put("project_name", itemData.get("project_name"));
            checkoutItem.put("sample_type", sampleEntity.getSampleType());
            checkoutItem.put("storage_location", storageLocation);
            checkoutSheet.add(checkoutItem);
        }
        String pdfUrl = Html2Pdf.html2pdf(checkoutSheetId, checkoutSheet);
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        result.put("pdf_url", pdfUrl);
        return result;
    }

    @Override
    public JSONObject findSamplesByCheckoutSheetId(int checkoutSheetId) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        List<ProjectSampleEntity> allByCheckoutSheetId = sampleRepository.findAllByCheckoutSheetId(checkoutSheetId);
        for (ProjectSampleEntity sampleEntity : allByCheckoutSheetId) {
            int sheetId = Integer.parseInt(sampleEntity.getInfoSheetId());
            Optional<ProjectSampleinfosheetEntity> sampleinfosheetEntityOptional = sampleinfosheetRepository.findById(sheetId);
            ProjectLabEntity labEntity = labRepository.findByLabId(sampleEntity.getLabId());
            if (labEntity == null) {
                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notFoundData();
            }
            ProjectProjectEntity projectEntity = projectRepository.findByProjectId(labEntity.getProjectId());
            if (projectEntity == null) {
                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return notFoundData();
            }
            double urgent = getUrgent(labEntity, projectEntity);
            if (sampleinfosheetEntityOptional.isPresent())
                data.add(generateData(sampleEntity, projectEntity, sampleinfosheetEntityOptional.get(), urgent));
            else
                data.add(generateData(sampleEntity, projectEntity, null, urgent));
        }
        result.put("data", data);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }



    @Override
    public JSONObject findSampleDetailById(int id) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray();
        Optional<ProjectSampleEntity> sampleEntityOptional = sampleRepository.findById(id);
        if (!sampleEntityOptional.isPresent()) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectSampleEntity sampleEntity = sampleEntityOptional.get();
        ProjectLabEntity labEntity = labRepository.findByLabId(sampleEntity.getLabId());
        if (labEntity == null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        ProjectProjectEntity projectEntity = projectRepository.findByProjectId(labEntity.getProjectId());
        if (projectEntity == null) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return notFoundData();
        }
        JSONObject dataItem = new JSONObject();
        dataItem.put("project_id", projectEntity.getProjectId());
        dataItem.put("project_name",projectEntity.getName());
        dataItem.put("lab_id", labEntity.getLabId());
        dataItem.put("lab_name",labEntity.getName());
        dataItem.put("spec",sampleEntity.getSpec());
        dataItem.put("sample_id",EAN13Generation.barcodeValidity("0001" + String.format("%08d", sampleEntity.getId())));
        dataItem.put("sample_name",sampleEntity.getName());
        dataItem.put("sample_type",sampleEntity.getSampleType());
        dataItem.put("volume",sampleEntity.getVolume());
        dataItem.put("concentration",sampleEntity.getConcentration());
        dataItem.put("total",sampleEntity.getTotal());
        dataItem.put("remainder",sampleEntity.getRemainder());
        dataItem.put("location",sampleEntity.getStorageLocation());
        dataItem.put("arrive_time",sampleEntity.getArrivedTime()==null?"":sampleEntity.getArrivedTime().toString());
        dataItem.put("return_time",sampleEntity.getReturnTime()==null?"":sampleEntity.getReturnTime().toString());
        dataItem.put("destroy_time",sampleEntity.getDestoryTime()==null?"":sampleEntity.getDestoryTime().toString());
        dataItem.put("receiver",sampleEntity.getReceiver());
        dataItem.put("remark",sampleEntity.getSampleRemark());
        data.add(dataItem);
        List<ProjectSampleEntity> sampleEntityList = sampleRepository.findAllByFatherSampleId(id);
        JSONArray sonSamples = new JSONArray();
        for (ProjectSampleEntity projectSampleEntity : sampleEntityList) {
            JSONObject item = new JSONObject();
            item.put("sample_id", EAN13Generation.barcodeValidity("0001" + String.format("%08d", projectSampleEntity.getId())));
            item.put("sample_name",projectSampleEntity.getName());
            item.put("sample_type",projectSampleEntity.getSampleType());
            sonSamples.add(item);
        }
        result.put("data", data);
        result.put("son_samples", sonSamples);
        result.put("code", "SUCCESS");
        result.put("error_description", new JSONArray());
        return result;
    }

    private void generateSampleEntity(ProjectSampleEntity sampleEntity,JSONObject sample){
        sampleEntity.setName(sample.getString("sample_name"));
        sampleEntity.setSampleType(sample.getString("sample_type"));
        sampleEntity.setVolume(sample.getString("volume"));
        sampleEntity.setConcentration(sample.getString("concentration"));
        sampleEntity.setTotal(sample.getDouble("total"));
        sampleEntity.setRemainder(sample.getDouble("remainder"));
        sampleEntity.setReceiver(sample.getString("receiver"));
        sampleEntity.setSampleRemark(sample.getString("remark"));
    }

    @Override
    public JSONObject modifySampleDetail(JSONObject sample) {
        String barcode = (String) sample.get("sample_id");
        int id = Integer.parseInt(barcode.substring(4, 12));
        ProjectSampleEntity sampleEntity = sampleRepository.findById(id).orElse(new ProjectSampleEntity());
        sampleEntity.setId(id);
        sampleEntity.setSpec(sample.getString("spec"));
        generateSampleEntity(sampleEntity,sample);
        sampleRepository.save(sampleEntity);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        result.put("error_description",new JSONArray());
        return result;
    }

    @Override
    public JSONObject subSampleAddition(JSONObject sample) {
        List<ProjectSampleEntity> sampleEntityList = sampleRepository.findAll();
        int id=0;
        for (ProjectSampleEntity sampleEntity:sampleEntityList)
            id = Math.max(id,sampleEntity.getId());
        id++;
        ProjectSampleEntity fatherSampleEntity = sampleRepository.findById(sample.getInt("father_sample_id")).orElse(new ProjectSampleEntity());
        fatherSampleEntity.setId(id);
        fatherSampleEntity.setFatherSampleId(sample.getInt("father_sample_id"));
        fatherSampleEntity.setInfoSheetId("");
        int fatherId = sample.getInt("father_sample_id");
        if (fatherId==-1){
            String labId = sample.getString("lab_id");
            String projectId = sample.getString("project_id");
            ProjectLabEntity labEntity = labRepository.findByLabId(labId);
            ProjectProjectEntity projectEntity = projectRepository.findByProjectId(projectId);
            if (labEntity==null)
                return errorReturnData("实验不存在");
            if (projectEntity==null)
                return errorReturnData("项目不存在");
            if (!labEntity.getProjectId().equals(projectId))
                return errorReturnData("实验和项目不匹配");
            fatherSampleEntity.setLabId(labId);
            fatherSampleEntity.setSpec(sample.getString("spec"));
            fatherSampleEntity.setArrivedTime(new Timestamp(System.currentTimeMillis()));
            fatherSampleEntity.setStatus("正常");
        }
        fatherSampleEntity.setStorageStatus("未入库");
        generateSampleEntity(fatherSampleEntity,sample);
        sampleRepository.save(fatherSampleEntity);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        result.put("error_description",new JSONArray());
        JSONArray data = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("sample_id",EAN13Generation.barcodeValidity("0001" + String.format("%08d", fatherSampleEntity.getId())));
        data.add(item);
        result.put("data",data);
        return result;
    }
}
