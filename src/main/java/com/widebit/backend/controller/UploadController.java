package com.widebit.backend.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UploadController {
    public static final String ROOT = "/tmp/lpp/upload-dir";
    @PostMapping(value = "/laboratory/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        JSONObject result = new JSONObject();
        try {
            String fileName = file.getOriginalFilename();
            String fileNam2 = UUID.randomUUID().toString();
            String imagePath = "/home/lpp/image/"+fileNam2+".png";
            File file1 = new File(imagePath);
            OutputStream os = new FileOutputStream(file1);
            os.write(file.getBytes());
            String contentPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            String imagePath2 = contentPath+"/image/"+fileNam2+".png";
            result.put("code","SUCCESS");
            JSONArray data = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("url",imagePath2);
            data.add(item);
            result.put("data",data);
            return result;
        }catch (Exception e){
            result.put("code","NOT_FOUND");
            JSONArray error = new JSONArray();
            error.add("NOT_FOUND");
            result.put("error_description",error);
            return result;
        }
    }
}
