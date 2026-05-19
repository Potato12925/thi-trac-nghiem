# Table: TaiKhoan

Columns:

- USERNAME varchar(50) PK
- PASSWORD_HASH varchar(255)
- ROLE varchar(20)
- MAGV nchar(8) FK -> GiaoVien.MAGV

Roles:

- PGV
- GIAOVIEN
- SINHVIEN

Rules:

- Store hashed passwords only
