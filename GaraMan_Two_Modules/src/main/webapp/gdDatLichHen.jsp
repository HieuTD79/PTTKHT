<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html lang="vi"><head><meta charset="UTF-8"><title>Đặt lịch hẹn - GaraMan</title><style>
body{font-family:Arial;background:#f4f6f8;margin:0;padding:35px}.box{max-width:620px;margin:auto;background:white;padding:28px;border-radius:12px;box-shadow:0 3px 18px #ddd}label{display:block;margin-top:14px;font-weight:bold}input,textarea{width:100%;padding:10px;margin-top:6px;box-sizing:border-box;border:1px solid #ccd2d8;border-radius:6px}button{margin-top:20px;padding:12px 20px;background:#087f5b;color:white;border:0;border-radius:6px;cursor:pointer}.ok{color:green}.err{color:#c00}</style></head><body><div class="box">
<h2>Đặt lịch hẹn trực tuyến</h2><% if(request.getAttribute("thongBao")!=null){ %><p class="ok">${thongBao}</p><% } %><% if(request.getAttribute("loi")!=null){ %><p class="err">${loi}</p><% } %>
<form method="post" action="${pageContext.request.contextPath}/dat-lich-hen">
<label>Họ và tên *</label><input name="hoTen" required maxlength="120">
<label>Số điện thoại *</label><input name="soDienThoai" required pattern="[0-9+ .-]{9,20}">
<label>Email</label><input type="email" name="email">
<label>Biển số xe *</label><input name="bienSoXe" required maxlength="20">
<label>Ngày hẹn *</label><input type="date" name="ngayHen" required min="<%=java.time.LocalDate.now()%>">
<label>Giờ hẹn *</label><select name="gioHen" required><% for(int h=8;h<=17;h++){for(int m:new int[]{0,30}){if(h==17&&m==30)continue;String t=String.format("%02d:%02d:00",h,m);%><option value="<%=t%>"><%=t.substring(0,5)%></option><%}} %></select>
<label>Ghi chú</label><textarea name="ghiChu" rows="3"></textarea><button type="submit">Xác nhận đặt lịch</button>
</form></div></body></html>
