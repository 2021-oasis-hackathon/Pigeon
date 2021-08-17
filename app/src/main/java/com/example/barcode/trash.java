package com.example.barcode;

public class trash {
    private String itemimage;
    public String barcodenum;
    private String name;
    private String material;
    private String recycle;

    public trash(){}

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getBarcode() { return barcodenum; }

    public void setBarcode(String barcodenum) {
        this.barcodenum = barcodenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRecycle() {
        return recycle;
    }

    public void setRecycle(String recycle) {
        this.recycle = recycle;
    }
}