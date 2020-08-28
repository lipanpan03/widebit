package com.widebit.backend.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface ISampleService {
    JSONObject findSamplesBySheetId(int sheetId);
    JSONObject findSamplesByCheckoutSheetId(int sheetId);
    JSONObject wrongFormat();
    JSONObject findSamplesBySampleId(int sampleId);
    JSONObject findAllSample();
    JSONObject saveSampleNote(int id,String status,String note,String imageNode);
    JSONObject prepareStoreIn(JSONArray samples);
    JSONObject notFoundData();
    JSONObject storeIn(JSONArray samples);
    JSONObject storeInCancel(JSONArray samples);
    JSONObject storeOut(JSONArray samples,String action) throws IOException;
    JSONObject findSampleDetailById(int id);
    JSONObject modifySampleDetail(JSONObject sample);
    JSONObject subSampleAddition(JSONObject sample);
}
