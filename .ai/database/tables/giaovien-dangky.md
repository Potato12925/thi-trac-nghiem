# Table: GiaoVien_DangKy

Composite PK:

- MALOP
- MAMH
- LAN

Columns:

- MAGV nchar(8) FK -> GiaoVien.MAGV
- MALOP nchar(15) FK -> Lop.MALOP
- MAMH nchar(5) FK -> MonHoc.MAMH
- TRINHDO nchar(1)
- NGAYTHI datetime
- LAN smallint
- SOCAUTHI smallint
- THOIGIAN smallint

Rules:

TRINHDO:

- A
- B
- C

LAN:

- 1-2

SOCAUTHI:

- 10-100

THOIGIAN:

- 5-60 minutes
