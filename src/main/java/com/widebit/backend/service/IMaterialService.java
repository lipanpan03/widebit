package com.widebit.backend.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Timestamp;

public interface IMaterialService {

    JSONObject materialAddition(JSONObject data);

    JSONObject findAllMaterial();

    JSONObject errorResult(String error,String errorDescription);

    JSONObject findMaterialById(int id);

    JSONObject findMaterialsBySheetId(int id);

    JSONObject materialOperation(JSONArray array, String operator, int status);

    JSONObject findOperationHistory(String type, Timestamp startTime, Timestamp endTime);

    JSONObject findOperationHistoryDetail(int id);

    JSONObject materialDetailModification(JSONObject data);

    JSONObject getOperationHistoryStatistic(String type, Timestamp startTime, Timestamp endTime);

    JSONObject modifyPurchaseDetail(JSONArray operation,String operator);

    JSONObject deleteMaterial(int id);
}
