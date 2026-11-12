package vn.garaman.dao;

import vn.garaman.config.DB;
import vn.garaman.model.LichHen;
import java.sql.*;

public class LichHenDAO {
    public boolean conCho(String ngay, String gio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tblLichHen WHERE ngayHen=? AND gioHen=? AND trangThai<>'DaHuy'";
        try (Connection c=DB.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(ngay)); p.setTime(2, Time.valueOf(gio));
            try (ResultSet r=p.executeQuery()) { r.next(); return r.getInt(1)==0; }
        }
    }

    public boolean taoLichHen(LichHen lh) throws SQLException {
        String sql = "INSERT INTO tblLichHen(hoTen,soDienThoai,email,bienSoXe,ngayHen,gioHen,ghiChu) VALUES(?,?,?,?,?,?,?)";
        try (Connection c=DB.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,lh.getHoTen()); p.setString(2,lh.getSoDienThoai()); p.setString(3,lh.getEmail());
            p.setString(4,lh.getBienSoXe()); p.setDate(5,Date.valueOf(lh.getNgayHen()));
            p.setTime(6,Time.valueOf(lh.getGioHen())); p.setString(7,lh.getGhiChu());
            return p.executeUpdate()==1;
        }
    }
}
