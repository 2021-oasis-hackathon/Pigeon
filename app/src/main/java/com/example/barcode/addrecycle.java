package com.example.barcode;

import android.widget.EditText;

public class addrecycle {
    private static String BaCord;//바코드 번호
    private static String NaMe; //제품 이름
    private static String MaTerial; // 제품 재질
    private static String ReCycle; // 분리수거 방법

    public addrecycle() {}

    public String getBaCord() {
        return BaCord;
    }

    public static void setBaCord(String baCord) {
        BaCord = baCord;
    }

    public String getNaMe() {
        return NaMe;
    }

    public static void setNaMe(String naMe) {
        NaMe = naMe;
    }

    public String getMaTerial() {
        return MaTerial;
    }

    public static void setMaTerial(String maTerial) {
        MaTerial = maTerial;
    }

    public String getReCycle() {
        return ReCycle;
    }

    public static void setReCycle(String reCycle) {
        ReCycle = reCycle;
    }
}

