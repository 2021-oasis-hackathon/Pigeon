package com.example.barcode;

public class addrecycle {

    private static String barcodeNum;//바코드 번호
    private static String naMe; //제품 이름
    private static String mateRial; // 제품 재질
    private static String recyCle; // 분리수거 방법

    public addrecycle() {}

    public String getBarcodenum() {
        return barcodeNum;
    }

    public static void setBarcodenum(String barcodenum) {
        barcodeNum = barcodenum;
    }

    public String getName() {
        return naMe;
    }

    public static void setName(String name) {
        naMe = name;
    }

    public String getMaterial() {
        return mateRial;
    }

    public static void setMaterial(String material) {
        mateRial = material;
    }

    public String getRecycle() {
        return recyCle;
    }

    public static void setRecycle(String recycle) {
        recyCle = recycle;
    }
}

