package com.widebit.backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EAN13Generation {

    public static String generateEAN13(String codeType, int id){
        Process proc;
        String line="";
        try {
            String[] arguments = new String[] {"python3.6", "/home/lpp/jar/util.py",codeType,id+"","/home/lpp/file/image/"};
            proc = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String imageLine = in.readLine();
            line = "http://119.3.255.23:8080/file/image/"+imageLine.substring(imageLine.lastIndexOf('/')+1);
            System.out.println(line);
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static String barcodeValidity(String barcode) {
        int s1 = 0;
        int s2 = 0;
        for (int i = 1; i <= 12; i++) {
            if (i % 2 == 1) {
                s1 = s1 + (barcode.charAt(i - 1) - '0');//奇数
            } else {
                s2 = s2 + (barcode.charAt(i - 1) - '0');//偶数
            }
        }
        s2 = s2 * 3;
        int c = 10 - (s1 + s2) % 10;
        c = c == 10 ? 0 : c;
        return barcode + c;
    }

    public static void main(String[] args) {
        generateEAN13("000",17);
    }
}
