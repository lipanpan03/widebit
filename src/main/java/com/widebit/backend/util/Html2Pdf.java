package com.widebit.backend.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

public class Html2Pdf {
    public static String html2pdf(int outId, JSONArray array) throws IOException {
        String htmlPath = "/home/lpp/file/html/checkout"+outId+".html";
        String pdfPath = "/home/lpp/file/html/checkout"+outId+".pdf";
        String page_header = "/home/lpp/file/html/page_header.html";
        BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(htmlPath));
        Document doc = Jsoup.parse(new File("/home/lpp/file/html/excelToHtml.html"), "utf-8");
        String imageUrl = EAN13Generation.generateEAN13("002",outId);
        Element ean13img = doc.getElementById("img_barcode");
        ean13img.attr("src",imageUrl);
        int idCount=1;
        for (Object o : array){
            JSONObject jsonObject = (JSONObject) o;
            Element checkoutTable = doc.getElementById("checkoutTable");
            String projectName=(String)jsonObject.get("project_name");
            String sampleType=(String)jsonObject.get("sample_type");
            String storageLocation=(String)jsonObject.get("storage_location");
            checkoutTable.append("<tr height='28' style='mso-height-source:userset;height:21pt' id='r2'>\n" +
                    "<td height='26' class='x21' style='height:19.5pt;'>"+idCount+"</td>" +
                    "<td class='x21'>"+jsonObject.get("sample_name")+"</td>" +
                    "<td class='x21'>"+jsonObject.get("id")+"</td>" +
                    "<td class='x21'>"+projectName+"</td>" +
                    "<td class='x21'>"+sampleType+"</td>" +
                    "<td class='x21'>"+storageLocation+"</td>" +
                    " </tr>");
            idCount++;
        }
        //System.out.println(doc.outerHtml());
        htmlWriter.write(doc.outerHtml());
        htmlWriter.flush();
        htmlWriter.close();
        String htmlToPdfCmd = "wkhtmltopdf --footer-center \"[page]\"  --header-html "+page_header+" "+htmlPath+" "+pdfPath;
        System.out.println(htmlToPdfCmd);
        try
        {
            Process process = Runtime.getRuntime().exec (htmlToPdfCmd);
            InputStreamReader ir=new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader (ir);
            String line;
            while ((line = input.readLine ()) != null){
                System.out.println(line);
            }
        }
        catch (IOException e){
            System.err.println ("IOException " + e.getMessage());
        }
        return "http://119.3.255.23:8080/file/html/checkout"+outId+".pdf";
    }

    public static void main(String[] args) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1");jsonObject.put("sample_name","1");jsonObject.put("project_name","1");
        jsonObject.put("sample_type","1");jsonObject.put("storage_location","1");
        jsonArray.add(jsonObject);
        System.out.println(html2pdf(17,jsonArray));
    }
}
