package vn.garaman.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class LichHen {
    private String hoTen, soDienThoai, email, bienSoXe, ghiChu;
    private LocalDate ngayHen;
    private LocalTime gioHen;

    public String getHoTen(){ return hoTen; } public void setHoTen(String v){hoTen=v;}
    public String getSoDienThoai(){ return soDienThoai; } public void setSoDienThoai(String v){soDienThoai=v;}
    public String getEmail(){ return email; } public void setEmail(String v){email=v;}
    public String getBienSoXe(){ return bienSoXe; } public void setBienSoXe(String v){bienSoXe=v;}
    public String getGhiChu(){ return ghiChu; } public void setGhiChu(String v){ghiChu=v;}
    public LocalDate getNgayHen(){ return ngayHen; } public void setNgayHen(LocalDate v){ngayHen=v;}
    public LocalTime getGioHen(){ return gioHen; } public void setGioHen(LocalTime v){gioHen=v;}
}
