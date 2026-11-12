package vn.garaman.model;

import java.math.BigDecimal;

public class Item {
    private int id, soLuong;
    private BigDecimal donGia;
    public Item(int id,int soLuong,BigDecimal donGia){this.id=id;this.soLuong=soLuong;this.donGia=donGia;}
    public int getId(){return id;} public int getSoLuong(){return soLuong;} public BigDecimal getDonGia(){return donGia;}
}
