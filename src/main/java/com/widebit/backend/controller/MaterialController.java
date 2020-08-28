package com.widebit.backend.controller;

import com.widebit.backend.service.IMaterialService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@ResponseBody
@CrossOrigin(origins = "*", maxAge = 3600)
public class MaterialController {

    @Autowired
    private IMaterialService materialService;

    @PostMapping(value = "/laboratory/material/addition")
    public JSONObject materialAddition(@RequestParam("material") String material){
        JSONObject sampleJson = JSONObject.fromObject(material);
        return materialService.materialAddition(sampleJson);
    }

    @RequestMapping(value = "/laboratory/material/query/{searchToken}/{searchField}",method = RequestMethod.GET)
    public JSONObject materialQuery(@PathVariable String searchToken, @PathVariable String searchField){
        if ("ALL".equals(searchField)&&"ALL".equals(searchToken)){
            return materialService.findAllMaterial();
        }
        if (searchToken.trim().length()==13){
            searchToken = searchToken.substring(1,13);
        }
        String type = searchToken.substring(0,3);
        int id;
        try {
            id = Integer.parseInt(searchToken.substring(3,11));
        }catch (Exception e){
            return materialService.errorResult("PARAMETER_ERROR","id "+searchToken+" 不存在");
        }
        switch (type) {
            case "003":
                return materialService.findMaterialsBySheetId(id);
            case "004":
                return materialService.findMaterialById(id);
            default:
                return materialService.errorResult("PARAMETER_ERROR","type "+ type + " error");
        }
    }

    @GetMapping(value = "/laboratory/material/list/type")
    public JSONObject getMaterialTypeList(){
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add("耗材类");array.add("酶抗体类");
        array.add("提取纯化类");array.add("化学试剂类");
        array.add("仪器设备类");array.add("办公装修类");
        array.add("测序服务类 ");
        result.put("data",array);
        return result;
    }

    @PostMapping(value = "/laboratory/material/operation")
    public JSONObject materialOperation(@RequestParam("material") String materialStr, @RequestParam("action")String action, @RequestParam("operator")String operator){
        JSONArray material = JSONArray.fromObject(materialStr);
        if ("MATERIAL_IN".equals(action)){
            return materialService.materialOperation(material,operator,1);
        }else if("MATERIAL_OUT".equals(action)){
            return materialService.materialOperation(material,operator,2);
        }else{
            return materialService.materialOperation(material,operator,3);
        }
    }

    @GetMapping(value = "/laboratory/material/history/list/type")
    public JSONObject getHistoryOperationTypeList(){
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add("全部记录");array.add("出库");
        array.add("入库");array.add("采购");
        result.put("data",array);
        return result;
    }

    @GetMapping(value = "/laboratory/material/history/list/time")
    public JSONObject getHistoryOperationTimeList(){
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add("近一月");array.add("近一周");
        array.add("近一年");array.add("输入范围");
        result.put("data",array);
        return result;
    }

    private Timestamp[] getStartTimeAndEndTime(String operationTime,String startTimeStr,String endTimeStr){
        Timestamp startTime,endTime;
        if ("近一月".equals(operationTime)){
            endTime=new Timestamp(System.currentTimeMillis());
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -1);
            startTime=new Timestamp(now.getTime().getTime());
        }else if ("近一周".equals(operationTime)){
            endTime=new Timestamp(System.currentTimeMillis());
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DATE, -7);
            startTime=new Timestamp(now.getTime().getTime());
        }else if ("近一年".equals(operationTime)){
            endTime=new Timestamp(System.currentTimeMillis());
            Calendar now = Calendar.getInstance();
            now.add(Calendar.YEAR, -1);
            startTime=new Timestamp(now.getTime().getTime());
        }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                startTime = new Timestamp(format.parse(startTimeStr).getTime());
                endTime = new Timestamp(format.parse(endTimeStr).getTime());
            }catch (Exception e){
                return new Timestamp[0];
            }
        }
        Timestamp[] result = new Timestamp[2];
        result[0]=startTime;
        result[1]=endTime;
        return result;
    }

    @PostMapping(value = "/laboratory/material/history/query")
    public JSONObject getOperationHistory(@RequestParam("operation_type") String operationType,@RequestParam("operation_time") String operationTime,@RequestParam("start_time") String startTimeStr,@RequestParam("end_time") String endTimeStr){
        Timestamp[] timestamps = getStartTimeAndEndTime(operationTime,startTimeStr,endTimeStr);
        if (timestamps.length==0)
            return materialService.errorResult("PARAMETER_ERROR","startTime or endTime error");
        return materialService.findOperationHistory(operationType,timestamps[0],timestamps[1]);
    }

    @PostMapping(value = "/laboratory/material/history/detail/query")
    public JSONObject getOperationHistoryDetail(@RequestParam("operation_id") int operationId, @RequestParam("operation_type") String operationType){
        return materialService.findOperationHistoryDetail(operationId);
    }

    @PostMapping(value = "/laboratory/material/detail/query")
    public JSONObject getSingleMaterialDetail(@RequestParam("id")String sid){
        int id = Integer.parseInt(sid.substring(4,12));
        return materialService.findMaterialById(id);
    }

    @PostMapping(value = "/laboratory/material/detail/modification")
    public JSONObject materialDetailModification(@RequestParam("material") String material){
        JSONObject sampleJson = JSONObject.fromObject(material);
        return materialService.materialDetailModification(sampleJson);
    }

    @GetMapping(value = "/laboratory/material/history/list/productstatus")
    public JSONObject getMaterialProduceStatusList(){
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add("已到货");array.add("未到货");
        result.put("data",array);
        return result;
    }

    @GetMapping(value = "/laboratory/material/history/list/invoicestatus")
    public JSONObject getMaterialInvoiceStatusList(){
        JSONObject result = new JSONObject();
        result.put("code","SUCCESS");
        JSONArray array = new JSONArray();
        array.add("预付");array.add("预付+开票");
        array.add("个人垫付");
        array.add("开票");array.add("未开票");
        result.put("data",array);
        return result;
    }

    @PostMapping(value = "/laboratory/material/history/statistic/query")
    public JSONObject getOperationHistoryStatistic(@RequestParam("operation_type") String operationType,@RequestParam("operation_time") String operationTime,@RequestParam("start_time") String startTimeStr,@RequestParam("end_time") String endTimeStr){
        Timestamp[] timestamps = getStartTimeAndEndTime(operationTime,startTimeStr,endTimeStr);
        if (timestamps.length==0)
            return materialService.errorResult("PARAMETER_ERROR","startTime or endTime error");
        return materialService.getOperationHistoryStatistic(operationType,timestamps[0],timestamps[1]);
    }

    @PostMapping(value = "/laboratory/material/detail/purchase/modification")
    public JSONObject modifyPurchaseDetail(@RequestParam("operation") String operation,@RequestParam("operator") String operator){
        return materialService.modifyPurchaseDetail(JSONArray.fromObject(operation),operator);
    }

    @PostMapping(value = "/laboratory/material/detail/delete")
    public JSONObject deleteMaterial(@RequestParam("id") String sid){
        return materialService.deleteMaterial(Integer.parseInt(sid.substring(4,12)));
    }
}
