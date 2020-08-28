package com.widebit.backend.controller;

import com.widebit.backend.service.ISampleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@ResponseBody
@CrossOrigin(origins = "*", maxAge = 3600)
public class SampleController {

    @Autowired
    private ISampleService sampleService;

    @RequestMapping(value = "/laboratory/samples/{searchToken}/{searchField}",method = RequestMethod.GET)
    public JSONObject getSampleList(@PathVariable String searchToken, @PathVariable String searchField){
        if ("ALL".equals(searchField)&&"ALL".equals(searchToken)){
            return sampleService.findAllSample();
        }
        if (searchToken.trim().length()==13){
            searchToken = searchToken.substring(1,13);
        }
        String type = searchToken.substring(0,3);
        int id;
        try {
            id = Integer.parseInt(searchToken.substring(3,11));
        }catch (Exception e){
            return sampleService.wrongFormat();
        }
        switch (type) {
            case "000":
                return sampleService.findSamplesBySheetId(id);
            case "001":
                return sampleService.findSamplesBySampleId(id);
            case "002":
                return sampleService.findSamplesByCheckoutSheetId(id);
            default:
                return sampleService.wrongFormat();
        }
    }

    @PostMapping(value = "/laboratory/samples/exception")
    public JSONObject getSampleList(@RequestParam("id") int id,@RequestParam("status") String status,@RequestParam("note") String note,@RequestParam("image_note") String imageNode){
        return sampleService.saveSampleNote(id,status,note,imageNode);
    }

    @PostMapping(value = "/laboratory/storage")
    public JSONObject storeSample(@RequestParam("samples") JSONArray samples, @RequestParam("action") String action) throws IOException {
        //JSONArray samples = new JSONArray();
        if ("SAMPLE_IN_PREPARE".equals(action)){
            return sampleService.prepareStoreIn(samples);
        }else if ("SAMPLE_IN".equals(action)){
            return sampleService.storeIn(samples);
        }else if ("SAMPLE_IN_CANCEL".equals(action)){
            return sampleService.storeInCancel(samples);
        }else if (action.startsWith("SAMPLE_OUT")){
            return sampleService.storeOut(samples,action);
        }
        return sampleService.notFoundData();
    }

    @PostMapping(value = "/laboratory/samples/details")
    public JSONObject sampleDetail(@RequestParam("id") int id){
        return sampleService.findSampleDetailById(id);
    }

    @PostMapping(value = "/laboratory/samples/details/modification")
    public JSONObject sampleDetailModification(@RequestParam("sample") String sample){
        JSONObject sampleJson = JSONObject.fromObject(sample);
        return sampleService.modifySampleDetail(sampleJson);
    }

    @PostMapping(value = "/laboratory/subsample/addition")
    public JSONObject subSampleAddition(@RequestParam("sample") String sample){
        JSONObject sampleJson = JSONObject.fromObject(sample);
        return sampleService.subSampleAddition(sampleJson);
    }
}
