# Table: ChiTietBaiThi

Columns:

- ID int PK identity
- BAITHI_ID int FK -> BaiThi.ID
- CAUHOI int FK -> BoDe.CAUHOI
- DAPAN_SV nchar(1)

Unique:

- BAITHI_ID + CAUHOI

Rules:

DAPAN_SV:

- A
- B
- C
- D
