package com.tudienghichu.chancahhoop.tudienghichu.QuanLy;

/**
 * Created by hoadt on 15/10/2017.
 */

public class QuanLy {
    private String maTV;
    private byte[] hinhAnh;
    private byte[] amThanhTiengViet;
    private byte[] amThanhTiengAnh;
    private String tiengViet;
    private String tiengAnh;
    private String ghiChu;
    public QuanLy() {
    }

    public QuanLy(String maTV, byte[] hinhAnh, byte[] amThanhTiengViet, byte[] amThanhTiengAnh, String tiengViet, String tiengAnh, String ghiChu) {
        this.maTV = maTV;
        this.hinhAnh = hinhAnh;
        this.amThanhTiengViet = amThanhTiengViet;
        this.amThanhTiengAnh = amThanhTiengAnh;
        this.tiengViet = tiengViet;
        this.tiengAnh = tiengAnh;
        this.ghiChu = ghiChu;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public byte[] getAmThanhTiengViet() {
        return amThanhTiengViet;
    }

    public void setAmThanhTiengViet(byte[] amThanhTiengViet) {
        this.amThanhTiengViet = amThanhTiengViet;
    }

    public byte[] getAmThanhTiengAnh() {
        return amThanhTiengAnh;
    }

    public void setAmThanhTiengAnh(byte[] amThanhTiengAnh) {
        this.amThanhTiengAnh = amThanhTiengAnh;
    }

    public String getTiengViet() {
        return tiengViet;
    }

    public void setTiengViet(String tiengViet) {
        this.tiengViet = tiengViet;
    }

    public String getTiengAnh() {
        return tiengAnh;
    }

    public void setTiengAnh(String tiengAnh) {
        this.tiengAnh = tiengAnh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
