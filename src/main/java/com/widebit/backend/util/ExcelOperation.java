package com.widebit.backend.util;

import net.sf.json.JSONArray;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelOperation {
    public static void inStock(String path, JSONArray data,String purchase,String receiver) throws IOException {
        String originPath = "file/in_stock.xlsx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(originPath));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row2 = sheet.getRow(1);
        XSSFCell cellDate = row2.createCell(1),cellPurchase = row2.createCell(3),cellReceiver = row2.createCell(5);
        cellDate.setCellValue(df.format(new Date()).substring(0,10));
        cellPurchase.setCellValue(purchase);
        cellReceiver.setCellValue(receiver);
        int idx=3,amount=0;
        double money=0.0;
        for (Object o : data){
            JSONArray item = (JSONArray) o;
            XSSFRow row = sheet.createRow(idx++);
            for (int i=0;i<9;i++){
                if (i==5){
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(item.getInt(i));
                }else if(i==6||i==7){
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(item.getDouble(i));
                }else{
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(item.getString(i));
                }
            }
            amount+=item.getInt(5);
            money+=item.getDouble(7);
        }
        XSSFRow row = sheet.createRow(idx);
        XSSFCell cellTotal = row.createCell(0);
        cellTotal.setCellValue("合计");
        XSSFCell cellTotalAmount = row.createCell(5);
        cellTotalAmount.setCellValue(amount);
        XSSFCell cellTotalMoney = row.createCell(7);
        cellTotalMoney.setCellValue(money);
        OutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
    }
    public static void main(String[] args) throws IOException {
        JSONArray data = new JSONArray();
        for (int i=0;i<5;i++){
            JSONArray item = new JSONArray();
            for (int j=0;j<10;j++){
                item.add(j);
            }
            data.add(item);
        }
        inStock("D:\\project_workspace\\ideaWorkspace\\widebit\\backend\\file\\material_instock1.xlsx",data,"lipanpan","lipanpan");
    }
}
