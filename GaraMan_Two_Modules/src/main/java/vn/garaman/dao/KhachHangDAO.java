package vn.garaman.dao;

import vn.garaman.config.DB;
import java.sql.*;

public class KhachHangDAO {
    public int themKhachHangMoi(Connection c,String ten,String sdt,String email,String bienSo) throws SQLException {
        String a="INSERT INTO tblThanhVien(hoTen,soDienThoai,email,vaiTro) VALUES(?,?,?,'KhachHang')";
        int id;
        try(PreparedStatement p=c.prepareStatement(a,Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,ten);p.setString(2,sdt);p.setString(3,email);p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){if(!r.next())throw new SQLException("Không tạo được khách hàng");id=r.getInt(1);}
        }
        try(PreparedStatement p=c.prepareStatement("INSERT INTO tblKhachHang(id,bienSoXe) VALUES(?,?)")){p.setInt(1,id);p.setString(2,bienSo);p.executeUpdate();}
        return id;
    }
}
