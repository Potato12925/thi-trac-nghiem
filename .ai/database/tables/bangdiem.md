# Table: BangDiem

Composite PK:

- MASV
- MAMH
- LAN

Columns:

- MASV nchar(8) FK -> SinhVien.MASV
- MAMH nchar(5) FK -> MonHoc.MAMH
- LAN smallint
- NGAYTHI date
- DIEM float

Rules:

LAN:

- 1-2

DIEM:

- 0-10
