# Table: BaiThi

Columns:

- ID int PK identity
- MASV nchar(8) FK -> SinhVien.MASV
- MAMH nchar(5) FK -> MonHoc.MAMH
- LAN smallint
- NGAYTHI datetime
- DIEM float
- THOIGIANBATDAU datetime
- THOIGIANKETTHUC datetime

Rules:

LAN:

- 1-2

DIEM:

- 0-10
