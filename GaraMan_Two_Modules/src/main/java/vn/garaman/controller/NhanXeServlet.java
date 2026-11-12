package vn.garaman.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.garaman.dao.*;
import vn.garaman.model.Item;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@WebServlet("/nhan-xe")
public class NhanXeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");
        try{
            HttpSession session=req.getSession(false);
            if(session==null||session.getAttribute("nhanVienId")==null)throw new IllegalArgumentException("Bạn cần đăng nhập bằng tài khoản nhân viên bán hàng.");
            int nvBanHangId=(Integer)session.getAttribute("nhanVienId");
            String ten=req.getParameter("hoTen"),sdt=req.getParameter("soDienThoai"),email=req.getParameter("email"),bienSo=req.getParameter("bienSoXe");
            int khId;
            String kh=req.getParameter("khachHangId");
            if(kh!=null&&!kh.isBlank()) khId=Integer.parseInt(kh);
            else {
                if(ten==null||ten.isBlank()||sdt==null||sdt.isBlank())throw new IllegalArgumentException("Nhập tên và số điện thoại khách hàng.");
                try(var c=vn.garaman.config.DB.getConnection()){c.setAutoCommit(false);try{khId=new KhachHangDAO().themKhachHangMoi(c,ten,sdt,email,bienSo);c.commit();}catch(Exception e){c.rollback();throw e;}}
            }
            int nvKyThuat=Integer.parseInt(req.getParameter("nvKyThuatId"));
            List<Item> dv=parseItems(req.getParameterValues("dichVuId"),req.getParameterValues("dichVuSoLuong"));
            List<Item> pt=parseItems(req.getParameterValues("phuTungId"),req.getParameterValues("phuTungSoLuong"));
            if(dv.isEmpty())throw new IllegalArgumentException("Phiếu tiếp nhận phải có ít nhất một dịch vụ.");
            int id=new PhieuTiepNhanDAO().taoPhieuTiepNhan(khId,nvBanHangId,nvKyThuat,bienSo,req.getParameter("moTaTinhTrang"),dv,pt);
            resp.sendRedirect(req.getContextPath()+"/inPhieuTiepNhan.jsp?id="+id);
        }catch(IllegalArgumentException e){req.setAttribute("loi",e.getMessage());try{req.setAttribute("dichVu",new DanhMucDAO().getList("tblDichVu"));req.setAttribute("phuTung",new DanhMucDAO().getList("tblPhuTung"));req.setAttribute("kyThuatVien",new DanhMucDAO().getKyThuatVien());}catch(Exception ignored){}req.getRequestDispatcher("/gdNhanXe.jsp").forward(req,resp);}
        catch(Exception e){throw new ServletException("Lỗi lập phiếu tiếp nhận: "+e.getMessage(),e);}
    }
    private List<Item> parseItems(String[] ids,String[] qty){
        List<Item> out=new ArrayList<>();if(ids==null)return out;
        for(int i=0;i<ids.length;i++){int n=Integer.parseInt(qty[i]);if(n<=0)throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");out.add(new Item(Integer.parseInt(ids[i]),n,BigDecimal.ZERO));}
        return out;
    }
}
