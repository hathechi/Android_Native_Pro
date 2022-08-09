package com.example.product.JavaClass;

public class ThuongHieu {
    private int id;
    private String tenthuonghieu;

    public ThuongHieu(int id, String tenthuonghieu) {
        this.id = id;
        this.tenthuonghieu = tenthuonghieu;
    }

    public ThuongHieu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenthuonghieu() {
        return tenthuonghieu;
    }

    public void setTenthuonghieu(String tenthuonghieu) {
        this.tenthuonghieu = tenthuonghieu;
    }
}
