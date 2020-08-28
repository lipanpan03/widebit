package com.widebit.backend.service;

import com.widebit.backend.entity.ProjectMaterialEntity;
import com.widebit.backend.entity.ProjectMaterialoperationhistoryEntity;
import com.widebit.backend.entity.ProjectMaterialoperationhistorydetailEntity;
import com.widebit.backend.entity.ProjectPurchaselistEntity;
import com.widebit.backend.repository.MaterialOperationHistoryDetailRepository;
import com.widebit.backend.repository.MaterialOperationHistoryRepository;
import com.widebit.backend.repository.MaterialRepository;
import com.widebit.backend.repository.PurchaseListRepository;
import com.widebit.backend.util.EAN13Generation;
import com.widebit.backend.util.ExcelOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService implements IMaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private PurchaseListRepository purchaseListRepository;

    @Autowired
    private MaterialOperationHistoryRepository materialOperationHistoryRepository;

    @Autowired
    private MaterialOperationHistoryDetailRepository materialOperationHistoryDetailRepository;

    private void generateMaterialEntity(ProjectMaterialEntity materialEntity,JSONObject data){
        materialEntity.setSupplier(data.getString("supplier"));
        materialEntity.setName(data.getString("name"));
        materialEntity.setBand(data.getString("band"));
        materialEntity.setNorm(data.getString("norm"));
        materialEntity.setPrice(data.getDouble("price"));
        materialEntity.setInstock(data.getInt("instock"));
        materialEntity.setNumber(data.getString("number"));
        materialEntity.setThreshold(data.getInt("threshold"));
        materialEntity.setUnit(data.getString("unit"));
        materialEntity.setType(data.getString("type"));
    }

    @Override
    public JSONObject materialAddition(JSONObject data) {
        JSONObject result = new JSONObject();
        try {
            ProjectMaterialEntity materialEntity = new ProjectMaterialEntity();
            generateMaterialEntity(materialEntity,data);
            materialEntity.setPurchase(data.getInt("instock"));
            materialRepository.save(materialEntity);
            result.put("code","SUCCESS");
            JSONArray inData = new JSONArray();
            JSONObject dataJson = new JSONObject();
            dataJson.put("materialID", EAN13Generation.barcodeValidity("0004" + String.format("%08d", materialEntity.getId())));
            inData.add(dataJson);
            result.put("data",inData);
        }catch (Exception e){
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("code","FAIL");
            JSONArray array = new JSONArray();
            array.add(e.getMessage());
            array.add("LACK_MATERIAL_FIELD");
            result.put("error_description",array);
        }
        return result;
    }

    private JSONObject generateMaterial(ProjectMaterialEntity materialEntity){
        JSONObject item = new JSONObject();
        item.put("materialID",EAN13Generation.barcodeValidity("0004" + String.format("%08d", materialEntity.getId())));
        item.put("supplier",materialEntity.getSupplier());
        item.put("stock_status",materialEntity.getInstock()>=materialEntity.getThreshold()?"正常":"紧急");
        item.put("name",materialEntity.getName());
        item.put("band",materialEntity.getBand());
        item.put("norm",materialEntity.getNorm());
        item.put("price",materialEntity.getPrice());
        item.put("instock",materialEntity.getInstock());
        item.put("number",materialEntity.getNumber());
        item.put("threshold",materialEntity.getThreshold());
        item.put("unit",materialEntity.getUnit());
        item.put("type",materialEntity.getType());
        return item;
    }

    @Override
    public JSONObject findAllMaterial() {
        List<ProjectMaterialEntity> materialEntityList = materialRepository.findAllByDeleteRemark(0);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        for (ProjectMaterialEntity materialEntity:materialEntityList){
            array.add(generateMaterial(materialEntity));
        }
        result.put("data",array);
        return result;
    }

    @Override
    public JSONObject findMaterialById(int id) {
        Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(id);
        if (!materialEntityOptional.isPresent()||materialEntityOptional.get().getDeleteRemark()==1) {
            ////TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("PARAMETER_ERROR","material id "+id+" not found");
        }
        ProjectMaterialEntity materialEntity = materialEntityOptional.get();
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add(generateMaterial(materialEntity));
        result.put("data",array);
        return result;
    }

    @Override
    public JSONObject findMaterialsBySheetId(int id) {
        List<ProjectPurchaselistEntity> purchaselistEntityList = purchaseListRepository.findAllBySheetId(id);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        for (ProjectPurchaselistEntity purchaseList:purchaselistEntityList){
            Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(purchaseList.getMaterialId());
            if (!materialEntityOptional.isPresent()||materialEntityOptional.get().getDeleteRemark()==1) continue;
            array.add(generateMaterial(materialEntityOptional.get()));
        }
        result.put("data",array);
        return result;
    }

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
    public JSONObject materialOperation(JSONArray array, String operator, int status) {
        JSONObject result = new JSONObject();
        JSONArray data = new JSONArray(), inStockArray = new JSONArray();
        int operationId = materialOperationHistoryRepository.findAll().size()+1;
        ProjectMaterialoperationhistoryEntity materialoperationhistoryEntity = new ProjectMaterialoperationhistoryEntity();
        materialoperationhistoryEntity.setId(operationId);
        if (status==1){
            materialoperationhistoryEntity.setType("入库");
            materialoperationhistoryEntity.setStatus("已完成");
        } else if (status==2){
            materialoperationhistoryEntity.setType("出库");
            materialoperationhistoryEntity.setStatus("已完成");
        } else if (status==3){
            materialoperationhistoryEntity.setType("采购");
            materialoperationhistoryEntity.setStatus("进行中");
        }
        materialoperationhistoryEntity.setOperator(operator);
        materialoperationhistoryEntity.setOperateTime(new Timestamp(System.currentTimeMillis()));
        int amount = 0;
        double money = 0.0;
        List<ProjectMaterialEntity> saveAllMaterialEntityList = new ArrayList<>();
        List<ProjectMaterialoperationhistorydetailEntity> saveAllMaterialoperationhistorydetailEntityList = new ArrayList<>();
        for (Object o:array){
            JSONObject item = JSONObject.fromObject(o);
            String sid = item.getString("id");
            data.add(sid);
            int id = Integer.parseInt(sid.substring(4,12));
            int count = item.getInt("count");
            amount += count;
            Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(id);
            if (!materialEntityOptional.isPresent()||materialEntityOptional.get().getDeleteRemark()==1) {
                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return errorResult("PARAMETER_ERROR","material id "+sid+" not found");
            }
            ProjectMaterialEntity materialEntity = materialEntityOptional.get();
            if (status==1){
                if (materialEntity.getInstock()+materialEntity.getOutstock()+count>materialEntity.getPurchase()){
                    //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return errorResult("PARAMETER_ERROR","物料"+sid+"入库失败，已采购数量不足");
                }
                materialEntity.setInstock(materialEntity.getInstock()+count);
                JSONArray inStockItemArray = new JSONArray();
                inStockItemArray.add(sid);
                inStockItemArray.add(materialEntity.getName());
                inStockItemArray.add(materialEntity.getNorm());
                inStockItemArray.add(materialEntity.getType());
                inStockItemArray.add(materialEntity.getUnit());
                inStockItemArray.add(count);
                inStockItemArray.add(materialEntity.getPrice());
                inStockItemArray.add(count*materialEntity.getPrice());
                inStockItemArray.add(materialEntity.getSupplier());
                inStockArray.add(inStockItemArray);
            } else if (status==2){
                if (count>materialEntity.getInstock()){
                    //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return errorResult("PARAMETER_ERROR","物料"+sid+"出库失败，库存不足");
                }
                materialEntity.setOutstock(materialEntity.getOutstock()+count);
                materialEntity.setInstock(materialEntity.getInstock()-count);
            }else if (status==3){
                materialEntity.setPurchase(materialEntity.getPurchase()+count);
            }
            money += count*materialEntity.getPrice();
            saveAllMaterialEntityList.add(materialEntity);
            //materialRepository.save(materialEntity);
            ProjectMaterialoperationhistorydetailEntity materialoperationhistorydetailEntity = new ProjectMaterialoperationhistorydetailEntity();
            materialoperationhistorydetailEntity.setMaterialId(id);
            if (status==3){
                materialoperationhistorydetailEntity.setProductStatus("未到货");
                materialoperationhistorydetailEntity.setInvoiceStatus(item.getString("invoice_status"));
            }else {
                materialoperationhistorydetailEntity.setProductStatus("");
                materialoperationhistorydetailEntity.setInvoiceStatus("");
            }
            materialoperationhistorydetailEntity.setMoney(count*materialEntity.getPrice());
            materialoperationhistorydetailEntity.setOperationId(operationId);
            materialoperationhistorydetailEntity.setCount(count);
            saveAllMaterialoperationhistorydetailEntityList.add(materialoperationhistorydetailEntity);
            //materialOperationHistoryDetailRepository.save(materialoperationhistorydetailEntity);
        }
        for (ProjectMaterialEntity materialEntity:saveAllMaterialEntityList){
            materialRepository.save(materialEntity);
        }
        for (ProjectMaterialoperationhistorydetailEntity materialoperationhistorydetailEntity:saveAllMaterialoperationhistorydetailEntityList){
            materialOperationHistoryDetailRepository.save(materialoperationhistorydetailEntity);
        }
        materialoperationhistoryEntity.setTotalMoney(money);
        materialoperationhistoryEntity.setTotalAmount(amount);
        materialOperationHistoryRepository.save(materialoperationhistoryEntity);
        int inStockId = materialOperationHistoryRepository.findAll().size();
        String path = "/home/lpp/file/material/in_stock"+inStockId+".xlsx";
        try {
            ExcelOperation.inStock(path,inStockArray,operator,operator);
        } catch (IOException e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("INTERVAL_ERROR","生成入库清单错误");
        }
        result.put("code","SUCCESS");
        result.put("data",data);
        result.put("in_stock_url","http://119.3.255.23:8080/file/material/in_stock"+inStockId+".xlsx");
        return result;
    }

    @Override
    public JSONObject findOperationHistory(String type, Timestamp startTime, Timestamp endTime) {
        List<ProjectMaterialoperationhistoryEntity> materialoperationhistoryEntityList;
        if ("全部记录".equals(type)){
            materialoperationhistoryEntityList = materialOperationHistoryRepository.findAllByOperateTimeBetweenOrderByOperateTimeDesc(startTime,endTime);
        }else{
            materialoperationhistoryEntityList = materialOperationHistoryRepository.findAllByTypeAndOperateTimeBetweenOrderByOperateTimeDesc(type,startTime,endTime);
        }
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        for (ProjectMaterialoperationhistoryEntity materialoperationhistoryEntity:materialoperationhistoryEntityList){
            JSONObject item = new JSONObject();
            item.put("id",materialoperationhistoryEntity.getId());
            item.put("type",materialoperationhistoryEntity.getType());
            item.put("status",materialoperationhistoryEntity.getStatus());
            item.put("totalMoney",materialoperationhistoryEntity.getTotalMoney());
            item.put("totalAmount",materialoperationhistoryEntity.getTotalAmount());
            item.put("operator",materialoperationhistoryEntity.getOperator());
            item.put("operation_time",materialoperationhistoryEntity.getOperateTime().toString().substring(0,10));
            //System.out.println(item.getString("operation_time"));
            array.add(item);
        }
        result.put("data",array);
        return result;
    }

    private JSONObject generateOperationHistoryDetail(ProjectMaterialEntity materialEntity,ProjectMaterialoperationhistorydetailEntity materialoperationhistorydetailEntity){
        JSONObject item = new JSONObject();
        item.put("id",materialoperationhistorydetailEntity.getId());
        item.put("materialID",EAN13Generation.barcodeValidity("0004" + String.format("%08d", materialEntity.getId())));
        item.put("name",materialEntity.getName());
        item.put("norm",materialEntity.getNorm());
        item.put("instock",materialEntity.getInstock());
        item.put("count",materialoperationhistorydetailEntity.getCount());
        item.put("unit",materialEntity.getUnit());
        item.put("product_status",materialoperationhistorydetailEntity.getProductStatus());
        item.put("invoice_status",materialoperationhistorydetailEntity.getInvoiceStatus());
        item.put("money",materialoperationhistorydetailEntity.getMoney());
        item.put("operator",materialoperationhistorydetailEntity.getOperator());
        return item;
    }

    @Override
    public JSONObject findOperationHistoryDetail(int id) {
        List<ProjectMaterialoperationhistorydetailEntity> materialoperationhistorydetailEntityList = materialOperationHistoryDetailRepository.findAllByOperationId(id);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        for (ProjectMaterialoperationhistorydetailEntity materialoperationhistorydetailEntity:materialoperationhistorydetailEntityList){
            Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(materialoperationhistorydetailEntity.getMaterialId());
            if (!materialEntityOptional.isPresent()) {
                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return errorResult("PARAMETER_ERROR","material id "+materialoperationhistorydetailEntity.getMaterialId()+" not found");
            }
            ProjectMaterialEntity materialEntity = materialEntityOptional.get();
            array.add(generateOperationHistoryDetail(materialEntity,materialoperationhistorydetailEntity));
        }
        result.put("data",array);
        return result;
    }

    @Override
    public JSONObject materialDetailModification(JSONObject data) {
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        int id = Integer.parseInt(data.getString("materialID").substring(4,12));
        Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(id);
        if (!materialEntityOptional.isPresent()||materialEntityOptional.get().getDeleteRemark()==1) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("PARAMETER_ERROR","material id "+id+" not found");
        }
        ProjectMaterialEntity materialEntity = materialEntityOptional.get();
        if (materialEntity.getPurchase()<=data.getInt("instock")) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("PARAMETER_ERROR","material id "+id+" purchase insufficiently");
        }
        generateMaterialEntity(materialEntity,data);
        materialRepository.save(materialEntity);
        array.add(data.getString("materialID"));
        result.put("data",array);
        return result;
    }

    @Override
    public JSONObject getOperationHistoryStatistic(String type, Timestamp startTime, Timestamp endTime) {
        List<ProjectMaterialoperationhistoryEntity> materialoperationhistorydetailEntityList = materialOperationHistoryRepository.findAllByTypeAndOperateTimeBetweenOrderByOperateTimeDesc(type,startTime,endTime);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        for (ProjectMaterialoperationhistoryEntity materialoperationhistoryEntity:materialoperationhistorydetailEntityList){
            for (ProjectMaterialoperationhistorydetailEntity materialoperationhistorydetailEntity:materialOperationHistoryDetailRepository.findAllByOperationId(materialoperationhistoryEntity.getId())){
                Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(materialoperationhistorydetailEntity.getMaterialId());
                if (!materialEntityOptional.isPresent()) {
                    //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return errorResult("PARAMETER_ERROR","material id "+materialoperationhistorydetailEntity.getMaterialId()+" not found");
                }
                ProjectMaterialEntity materialEntity = materialEntityOptional.get();
                array.add(generateOperationHistoryDetail(materialEntity,materialoperationhistorydetailEntity));
            }
        }
        result.put("data",array);
        return result;
    }

    @Override
    public JSONObject modifyPurchaseDetail(JSONArray operation, String operator) {
        if (operation.size()==0) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("PARAMETER_ERROR","size of operation is 0");
        }
        boolean flag = true;
        int operationId = 0;
        for (Object o:operation){
            JSONObject item = JSONObject.fromObject(o);
            int id = item.getInt("id");
            String invoiceStatus = item.getString("invoice_status");
            String productStatus = item.getString("product_status");
            Optional<ProjectMaterialoperationhistorydetailEntity> materialoperationhistorydetailEntityOptional = materialOperationHistoryDetailRepository.findById(id);
            if (!materialoperationhistorydetailEntityOptional.isPresent()) {
                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return errorResult("PARAMETER_ERROR","material detail operation id "+id+" not found");
            }
            ProjectMaterialoperationhistorydetailEntity materialoperationhistorydetailEntity = materialoperationhistorydetailEntityOptional.get();
            materialoperationhistorydetailEntity.setProductStatus(productStatus);
            materialoperationhistorydetailEntity.setInvoiceStatus(invoiceStatus);
            materialOperationHistoryDetailRepository.save(materialoperationhistorydetailEntity);
            operationId=materialoperationhistorydetailEntity.getOperationId();
            if ("未到货".equals(productStatus)) flag=false;
        }
        Optional<ProjectMaterialoperationhistoryEntity> materialoperationhistoryEntityOptional = materialOperationHistoryRepository.findById(operationId);
        if (!materialoperationhistoryEntityOptional.isPresent()) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("INTERVAL_ERROR","operation history is not found");
        }
        ProjectMaterialoperationhistoryEntity materialoperationhistoryEntity = materialoperationhistoryEntityOptional.get();
        if (flag){
            materialoperationhistoryEntity.setStatus("已完成");
        }
        materialoperationhistoryEntity.setOperator(operator);
        materialOperationHistoryRepository.save(materialoperationhistoryEntity);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add(operationId);
        result.put("data",array);
        return result;
    }

    @Override
    public JSONObject deleteMaterial(int id) {
        Optional<ProjectMaterialEntity> materialEntityOptional = materialRepository.findById(id);
        if (!materialEntityOptional.isPresent()||materialEntityOptional.get().getDeleteRemark()==1) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return errorResult("PARAMETER_ERROR","material id "+id+" not found");
        }
        ProjectMaterialEntity materialEntity = materialEntityOptional.get();
        materialEntity.setDeleteRemark(1);
        materialRepository.save(materialEntity);
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray data = new JSONArray();
        data.add(EAN13Generation.barcodeValidity("0004" + String.format("%08d", materialEntity.getId())));
        result.put("data",data);
        return result;
    }
}
