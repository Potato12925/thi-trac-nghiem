# Database Relationships

SinhVien.MALOP -> Lop.MALOP

TaiKhoan.MAGV -> GiaoVien.MAGV

GiaoVien_DangKy:

- MAGV -> GiaoVien.MAGV
- MALOP -> Lop.MALOP
- MAMH -> MonHoc.MAMH

BoDe:

- MAMH -> MonHoc.MAMH
- MAGV -> GiaoVien.MAGV

BaiThi:

- MASV -> SinhVien.MASV
- MAMH -> MonHoc.MAMH

ChiTietBaiThi:

- BAITHI_ID -> BaiThi.ID
- CAUHOI -> BoDe.CAUHOI

BangDiem:

- MASV -> SinhVien.MASV
- MAMH -> MonHoc.MAMH
