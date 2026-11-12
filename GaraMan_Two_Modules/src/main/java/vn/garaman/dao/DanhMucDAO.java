package vn.garaman.dao;

import vn.garaman.config.DB;
import java.sql.*;
import java.util.*;

public class DanhMucDAO {
    public List<Map<String,Object>> getList(String table) throws SQLException {
        if (!table.equals("tblDichVu") && !table.equals("tblPhuTung")) throw new IllegalArgumentException("Bảng không hợp lệ");
        String sql = table.equals("tblDichVu")
          ? "SELECT id,tenDichVu ten,donGia gia,1 soLuongTon FROM tblDichVu WHERE trangThai='HoatDong'"
          : "SELECT id,tenPhuTung ten,donGia gia,soLuongTon FROM tblPhuTung WHERE trangThai='HoatDong'";
        List<Map<String,Object>> list=new ArrayList<>();
        try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){
            while(r.next()){Map<String,Object> m=new HashMap<>();m.put("id",r.getInt("id"));m.put("ten",r.getString("ten"));m.put("gia",r.getBigDecimal("gia"));m.put("ton",r.getInt("soLuongTon"));list.add(m);}
        } return list;
    }
    public List<Map<String,Object>> getKyThuatVien() throws SQLException {
        List<Map<String,Object>> list=new ArrayList<>();
        String sql="SELECT n.id,t.hoTen FROM tblNhanVien n JOIN tblThanhVien t ON n.id=t.id WHERE n.viTri='NVKyThuat' AND n.trangThai='HoatDong'";
        try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){
            while(r.next()){Map<String,Object> m=new HashMap<>();m.put("id",r.getInt(1));m.put("hoTen",r.getString(2));list.add(m);}
        } return list;
    }
    public List<Map<String,Object>> timKhachHang(String q) throws SQLException {
        List<Map<String,Object>> list=new ArrayList<>();
        String sql="SELECT k.id,t.hoTen,t.soDienThoai,t.email,k.bienSoXe FROM tblKhachHang k JOIN tblThanhVien t ON k.id=t.id WHERE t.hoTen LIKE ? OR t.soDienThoai LIKE ?";
        try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setString(1,"%"+q+"%");p.setString(2,"%"+q+"%");try(ResultSet r=p.executeQuery()){while(r.next()){Map<String,Object> m=new HashMap<>();m.put("id",r.getInt("id"));m.put("hoTen",r.getString("hoTen"));m.put("sdt",r.getString("soDienThoai"));m.put("email",r.getString("email"));m.put("bienSo",r.getString("bienSoXe"));list.add(m);}}}
        return list;
    }
}
