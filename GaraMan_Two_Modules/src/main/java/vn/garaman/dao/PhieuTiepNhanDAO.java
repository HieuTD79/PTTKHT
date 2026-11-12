package vn.garaman.dao;

import vn.garaman.config.DB;
import vn.garaman.model.Item;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;

public class PhieuTiepNhanDAO {
    private BigDecimal layDonGia(Connection c,String table,int id) throws SQLException {
        if(!table.equals("tblDichVu") && !table.equals("tblPhuTung")) throw new SQLException("Danh mục không hợp lệ");
        try(PreparedStatement p=c.prepareStatement("SELECT donGia FROM "+table+" WHERE id=? AND trangThai='HoatDong'")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Không tìm thấy dịch vụ/phụ tùng ID "+id);return r.getBigDecimal(1);}
        }
    }

    public int taoPhieuTiepNhan(int khId,int nvBanHangId,int nvKyThuatId,String bienSo,String moTa,
                                List<Item> dichVu,List<Item> phuTung) throws SQLException {
        try(Connection c=DB.getConnection()){
            c.setAutoCommit(false);
            try {
                BigDecimal tong=BigDecimal.ZERO;
                for(Item i:dichVu) tong=tong.add(i.getDonGia().multiply(BigDecimal.valueOf(i.getSoLuong())));
                for(Item i:phuTung) tong=tong.add(i.getDonGia().multiply(BigDecimal.valueOf(i.getSoLuong())));
                String sql="INSERT INTO tblPhieuTiepNhan(khachHangId,nvBanHangId,nvKyThuatId,bienSoXe,moTaTinhTrang,tongTien) VALUES(?,?,?,?,?,?)";
                int phieuId;
                try(PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
                    p.setInt(1,khId);p.setInt(2,nvBanHangId);p.setInt(3,nvKyThuatId);p.setString(4,bienSo);p.setString(5,moTa);p.setBigDecimal(6,tong);p.executeUpdate();
                    try(ResultSet r=p.getGeneratedKeys()){r.next();phieuId=r.getInt(1);}
                }
                String dv="INSERT INTO tblChiTietDichVu(phieuTiepNhanId,dichVuId,soLuong,donGia,thanhTien) VALUES(?,?,?,?,?)";
                for(Item i:dichVu) { BigDecimal gia=layDonGia(c,"tblDichVu",i.getId()); try(PreparedStatement p=c.prepareStatement(dv)){p.setInt(1,phieuId);p.setInt(2,i.getId());p.setInt(3,i.getSoLuong());p.setBigDecimal(4,gia);p.setBigDecimal(5,gia.multiply(BigDecimal.valueOf(i.getSoLuong())));p.executeUpdate();} }
                String pt="INSERT INTO tblChiTietPhuTung(phieuTiepNhanId,phuTungId,soLuong,donGia,thanhTien) VALUES(?,?,?,?,?)";
                for(Item i:phuTung){
                    try(PreparedStatement lock=c.prepareStatement("SELECT soLuongTon,donGia FROM tblPhuTung WHERE id=? FOR UPDATE")){lock.setInt(1,i.getId());try(ResultSet r=lock.executeQuery()){if(!r.next()||r.getInt(1)<i.getSoLuong())throw new SQLException("Phụ tùng không đủ tồn kho (ID "+i.getId()+")");}}
                    try(PreparedStatement p=c.prepareStatement(pt)){p.setInt(1,phieuId);p.setInt(2,i.getId());p.setInt(3,i.getSoLuong());p.setBigDecimal(4,i.getDonGia());p.setBigDecimal(5,i.getDonGia().multiply(BigDecimal.valueOf(i.getSoLuong())));p.executeUpdate();}
                    try(PreparedStatement p=c.prepareStatement("UPDATE tblPhuTung SET soLuongTon=soLuongTon-? WHERE id=? AND soLuongTon>=?")){p.setInt(1,i.getSoLuong());p.setInt(2,i.getId());p.setInt(3,i.getSoLuong());if(p.executeUpdate()!=1)throw new SQLException("Không thể cập nhật tồn kho");}
                }
                c.commit(); return phieuId;
            } catch(Exception e){c.rollback();if(e instanceof SQLException)throw (SQLException)e;throw new SQLException(e);}
            finally{c.setAutoCommit(true);}
        }
    }
}
