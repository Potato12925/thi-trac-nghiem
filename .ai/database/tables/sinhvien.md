# Table: SinhVien

Columns:

- MASV nchar(8) PK
- HO nvarchar(40)
- TEN nvarchar(10)
- NGAYSINH date
- DIACHI nvarchar(100)
- MALOP nchar(15) FK -> Lop.MALOP

Relationships:

- Many SinhVien -> One Lop
