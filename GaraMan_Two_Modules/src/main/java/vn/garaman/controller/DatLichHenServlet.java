package vn.garaman.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.garaman.dao.LichHenDAO;
import vn.garaman.model.LichHen;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/dat-lich-hen")
public class DatLichHenServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String hoTen=req.getParameter("hoTen"), sdt=req.getParameter("soDienThoai");
            String bienSo=req.getParameter("bienSoXe"), ngay=req.getParameter("ngayHen"), gio=req.getParameter("gioHen");
            if (hoTen==null || hoTen.isBlank() || sdt==null || sdt.isBlank() || bienSo==null || bienSo.isBlank())
                throw new IllegalArgumentException("Vui lòng nhập họ tên, số điện thoại và biển số xe.");
            LocalDate d=LocalDate.parse(ngay); LocalTime t=LocalTime.parse(gio);
            if (d.isBefore(LocalDate.now())) throw new IllegalArgumentException("Ngày hẹn không hợp lệ.");
            LichHen lh=new LichHen(); lh.setHoTen(hoTen.trim()); lh.setSoDienThoai(sdt.trim());
            lh.setEmail(req.getParameter("email")); lh.setBienSoXe(bienSo.trim().toUpperCase());
            lh.setNgayHen(d); lh.setGioHen(t); lh.setGhiChu(req.getParameter("ghiChu"));
            LichHenDAO dao=new LichHenDAO();
            if (!dao.conCho(ngay,gio)) throw new IllegalArgumentException("Khung giờ này đã có người đặt. Vui lòng chọn giờ khác.");
            dao.taoLichHen(lh);
            req.setAttribute("thongBao","Đặt lịch hẹn thành công!");
            req.getRequestDispatcher("/gdDatLichHen.jsp").forward(req,resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("loi",e.getMessage()); req.getRequestDispatcher("/gdDatLichHen.jsp").forward(req,resp);
        } catch (Exception e) {
            throw new ServletException("Không thể đặt lịch hẹn: "+e.getMessage(),e);
        }
    }
}
