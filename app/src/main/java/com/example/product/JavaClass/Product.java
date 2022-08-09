package com.example.product.JavaClass;

public class Product {
    private int id;
    private String tenSp;
    private Float giaSp;
    private String moTA;
    private String ThuongHieu;
    private byte[] ImageSp;


    public Product() {
    }

    public Product(int id, String tenSp, Float giaSp, String moTA, String thuongHieu, byte[] imageSp) {
        this.id = id;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.moTA = moTA;
        ThuongHieu = thuongHieu;
        ImageSp = imageSp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Float getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(Float giaSp) {
        this.giaSp = giaSp;
    }

    public String getMoTA() {
        return moTA;
    }

    public void setMoTA(String moTA) {
        this.moTA = moTA;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        ThuongHieu = thuongHieu;
    }

    public byte[] getImageSp() {
        return ImageSp;
    }

    public void setImageSp(byte[] imageSp) {
        ImageSp = imageSp;
    }
}
