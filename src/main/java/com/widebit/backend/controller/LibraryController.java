package com.widebit.backend.controller;

import com.widebit.backend.service.ILibraryService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@CrossOrigin(origins = "*", maxAge = 3600)
public class LibraryController {

    @Autowired
    private ILibraryService libraryService;

    @PostMapping(value = "/laboratory/library/project/query")
    public JSONObject postProjectInformation(@RequestParam("sample_id") String strSampleId){
        int sampleId = Integer.parseInt(strSampleId.substring(4,12));
        return libraryService.getProjectInformation(sampleId);
    }

    @GetMapping(value = "/laboratory/library/sequencing/list")
    public JSONObject getSequencingList(){
        return null;
    }
}
