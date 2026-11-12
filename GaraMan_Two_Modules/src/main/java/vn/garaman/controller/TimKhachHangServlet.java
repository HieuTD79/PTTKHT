package vn.garaman.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.garaman.dao.DanhMucDAO;
import java.io.IOException;
@WebServlet("/tim-khach-hang")
public class TimKhachHangServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
  req.setCharacterEncoding("UTF-8");try{req.setAttribute("khachHang",new DanhMucDAO().timKhachHang(req.getParameter("q")==null?"":req.getParameter("q")));req.setAttribute("dichVu",new DanhMucDAO().getList("tblDichVu"));req.setAttribute("phuTung",new DanhMucDAO().getList("tblPhuTung"));req.setAttribute("kyThuatVien",new DanhMucDAO().getKyThuatVien());req.getRequestDispatcher("/gdNhanXe.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}
 }
}
