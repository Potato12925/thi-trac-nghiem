# Table: BoDe

Columns:

- CAUHOI int PK identity
- MAMH nchar(5) FK -> MonHoc.MAMH
- TRINHDO char(1)
- NOIDUNG nvarchar(200)
- A nvarchar(250)
- B nvarchar(250)
- C nvarchar(250)
- D nvarchar(250)
- DAP_AN nchar(1)
- MAGV nchar(8) FK -> GiaoVien.MAGV

Rules:

TRINHDO:

- A
- B
- C

DAP_AN:

- A
- B
- C
- D
