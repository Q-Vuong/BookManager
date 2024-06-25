package com.example.booksmanagerager.model;

public class NguoiDung {
    private String tendangnhap;
    private String matkhau;
    private String hoten;

    public NguoiDung() {
    }

    public NguoiDung(String tendangnhap, String matkhau, String hoten) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.hoten = hoten;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
