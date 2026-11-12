<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<%
List<Map<String,Object>> dvs=(List<Map<String,Object>>)request.getAttribute("dichVu");
List<Map<String,Object>> pts=(List<Map<String,Object>>)request.getAttribute("phuTung");
List<Map<String,Object>> techs=(List<Map<String,Object>>)request.getAttribute("kyThuatVien");
if(dvs==null||pts==null||techs==null){try{vn.garaman.dao.DanhMucDAO dao=new vn.garaman.dao.DanhMucDAO();dvs=dao.getList("tblDichVu");pts=dao.getList("tblPhuTung");techs=dao.getKyThuatVien();}catch(Exception e){out.print("Lỗi tải dữ liệu: "+e.getMessage());}}
%>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Nhận xe - GaraMan</title><style>body{font-family:Arial;background:#f4f6f8;padding:25px}.wrap{max-width:950px;margin:auto;background:white;padding:25px;border-radius:10px}label{display:block;margin-top:12px;font-weight:bold}input,select,textarea{padding:9px;margin:5px 0;width:100%;box-sizing:border-box}fieldset{margin-top:18px;border:1px solid #ddd}button{padding:10px 16px;background:#087f5b;color:white;border:0;border-radius:5px;margin-top:15px}.err{color:red}</style></head><body><div class="wrap">
<h2>Phiếu tiếp nhận xe</h2><%if(request.getAttribute("loi")!=null){%><p class="err"><%=request.getAttribute("loi")%></p><%}%>
<form method="post" action="${pageContext.request.contextPath}/nhan-xe">
<fieldset><legend>Thông tin khách hàng</legend>
<label>ID khách hàng có sẵn (nếu đã tìm thấy)</label><input name="khachHangId" type="number" min="1">
<label>Tìm khách hàng theo tên hoặc SĐT</label><form method="get" action="${pageContext.request.contextPath}/tim-khach-hang"><input name="q" placeholder="Tên hoặc số điện thoại"><button type="submit">Tìm khách hàng</button></form>
<label>Họ tên (nếu tạo mới)</label><input name="hoTen"><label>Số điện thoại</label><input name="soDienThoai"><label>Email</label><input type="email" name="email">
<label>Biển số xe *</label><input name="bienSoXe" required>
</fieldset>
<fieldset><legend>Dịch vụ (chọn ít nhất một)</legend><%if(dvs!=null)for(Map<String,Object> x:dvs){%><label><input style="width:auto" type="checkbox" name="dichVuId" value="<%=x.get("id")%>"> <%=x.get("ten")%> - <%=x.get("gia")%> VNĐ</label><input type="number" name="dvQty_<%=x.get("id")%>" min="1" value="1"><%}%></fieldset>
<fieldset><legend>Phụ tùng</legend><%if(pts!=null)for(Map<String,Object> x:pts){%><label><input style="width:auto" type="checkbox" name="phuTungId" value="<%=x.get("id")%>"> <%=x.get("ten")%> - <%=x.get("gia")%> VNĐ (Tồn: <%=x.get("ton")%>)</label><input type="number" name="ptQty_<%=x.get("id")%>" min="1" value="1"><%}%></fieldset>
<label>Kỹ thuật viên *</label><select name="nvKyThuatId" required><option value="">-- Chọn kỹ thuật viên --</option><%if(techs!=null)for(Map<String,Object>x:techs){%><option value="<%=x.get("id")%>"><%=x.get("hoTen")%></option><%}%></select>
<label>Mô tả tình trạng xe</label><textarea name="moTaTinhTrang" rows="3"></textarea><button type="submit">Lưu và in phiếu tiếp nhận</button></form></div></body></html>
