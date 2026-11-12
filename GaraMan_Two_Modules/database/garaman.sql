CREATE DATABASE IF NOT EXISTS garaman CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE garaman;

CREATE TABLE tblThanhVien (
 id INT AUTO_INCREMENT PRIMARY KEY, hoTen VARCHAR(120) NOT NULL, soDienThoai VARCHAR(20) NOT NULL,
 email VARCHAR(120), diaChi VARCHAR(255), matKhau VARCHAR(255), vaiTro VARCHAR(30) NOT NULL DEFAULT 'KhachHang'
);
CREATE TABLE tblKhachHang (
 id INT PRIMARY KEY, bienSoXe VARCHAR(20),
 CONSTRAINT fk_kh_tv FOREIGN KEY(id) REFERENCES tblThanhVien(id)
);
CREATE TABLE tblNhanVien (
 id INT PRIMARY KEY, viTri VARCHAR(30) NOT NULL, chuyenMon VARCHAR(120), trangThai VARCHAR(30) NOT NULL DEFAULT 'HoatDong',
 CONSTRAINT fk_nv_tv FOREIGN KEY(id) REFERENCES tblThanhVien(id)
);
CREATE TABLE tblLichHen (
 id INT AUTO_INCREMENT PRIMARY KEY, khachHangId INT NULL, hoTen VARCHAR(120) NOT NULL, soDienThoai VARCHAR(20) NOT NULL,
 email VARCHAR(120), bienSoXe VARCHAR(20) NOT NULL, ngayHen DATE NOT NULL, gioHen TIME NOT NULL,
 ghiChu VARCHAR(500), trangThai VARCHAR(30) NOT NULL DEFAULT 'ChoXacNhan', ngayTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_lh_kh FOREIGN KEY(khachHangId) REFERENCES tblKhachHang(id),
 UNIQUE KEY uq_lichhen_slot(ngayHen, gioHen, trangThai)
);
CREATE TABLE tblDichVu (
 id INT AUTO_INCREMENT PRIMARY KEY, tenDichVu VARCHAR(150) NOT NULL, moTa VARCHAR(500), donGia DECIMAL(15,2) NOT NULL DEFAULT 0,
 trangThai VARCHAR(30) NOT NULL DEFAULT 'HoatDong'
);
CREATE TABLE tblPhuTung (
 id INT AUTO_INCREMENT PRIMARY KEY, tenPhuTung VARCHAR(150) NOT NULL, donViTinh VARCHAR(30), donGia DECIMAL(15,2) NOT NULL DEFAULT 0,
 soLuongTon INT NOT NULL DEFAULT 0, trangThai VARCHAR(30) NOT NULL DEFAULT 'HoatDong'
);
CREATE TABLE tblPhieuTiepNhan (
 id INT AUTO_INCREMENT PRIMARY KEY, khachHangId INT NOT NULL, nvBanHangId INT NOT NULL, nvKyThuatId INT NOT NULL,
 bienSoXe VARCHAR(20) NOT NULL, moTaTinhTrang VARCHAR(500), tongTien DECIMAL(15,2) NOT NULL DEFAULT 0,
 ngayTiepNhan TIMESTAMP DEFAULT CURRENT_TIMESTAMP, trangThai VARCHAR(30) NOT NULL DEFAULT 'DaTiepNhan',
 FOREIGN KEY(khachHangId) REFERENCES tblKhachHang(id),
 FOREIGN KEY(nvBanHangId) REFERENCES tblNhanVien(id),
 FOREIGN KEY(nvKyThuatId) REFERENCES tblNhanVien(id)
);
CREATE TABLE tblChiTietDichVu (
 id INT AUTO_INCREMENT PRIMARY KEY, phieuTiepNhanId INT NOT NULL, dichVuId INT NOT NULL,
 soLuong INT NOT NULL DEFAULT 1, donGia DECIMAL(15,2) NOT NULL, thanhTien DECIMAL(15,2) NOT NULL,
 FOREIGN KEY(phieuTiepNhanId) REFERENCES tblPhieuTiepNhan(id),
 FOREIGN KEY(dichVuId) REFERENCES tblDichVu(id)
);
CREATE TABLE tblChiTietPhuTung (
 id INT AUTO_INCREMENT PRIMARY KEY, phieuTiepNhanId INT NOT NULL, phuTungId INT NOT NULL,
 soLuong INT NOT NULL, donGia DECIMAL(15,2) NOT NULL, thanhTien DECIMAL(15,2) NOT NULL,
 FOREIGN KEY(phieuTiepNhanId) REFERENCES tblPhieuTiepNhan(id),
 FOREIGN KEY(phuTungId) REFERENCES tblPhuTung(id)
);

INSERT INTO tblThanhVien(hoTen,soDienThoai,email,vaiTro) VALUES
('Nhân viên bán hàng mẫu','0900000001','sales@garaman.local','NhanVien'),
('Kỹ thuật viên mẫu','0900000002','tech@garaman.local','NhanVien');
INSERT INTO tblNhanVien(id,viTri,chuyenMon) VALUES (1,'NVBanHang',NULL),(2,'NVKyThuat','Sửa chữa tổng hợp');
INSERT INTO tblDichVu(tenDichVu,moTa,donGia) VALUES
('Thay dầu','Thay dầu động cơ',150000),('Kiểm tra tổng quát','Kiểm tra tình trạng xe',100000);
INSERT INTO tblPhuTung(tenPhuTung,donViTinh,donGia,soLuongTon) VALUES
('Lọc dầu','Cái',80000,20),('Bugi','Cái',120000,15);
