package com.etiya.onlinebilet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public class Utils {

    public static String uretPnrNo(int uzunluk){
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String values = chars + numbers;

        char[] pnrNo = new char[uzunluk];
        for (int i = 0; i < uzunluk; i++){
            pnrNo[i] = values.charAt(random.nextInt(values.length()));
        }
        return new String(pnrNo);
    }

    public static String uretUcusNo(String ucusKodu, int uzunluk){
        Random random = new Random();
        int sayisalUzunluk = uzunluk - ucusKodu.length();
        return ucusKodu + random.nextInt((int)Math.pow(10,sayisalUzunluk)-1);
    }

    public static void main(String[] args) {
        System.out.println(uretUcusNo("PC",6));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
