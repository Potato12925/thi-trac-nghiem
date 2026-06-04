USE master;
GO

IF DB_ID('THITRACNGHIEM') IS NOT NULL
BEGIN
    ALTER DATABASE THITRACNGHIEM
    SET SINGLE_USER
    WITH ROLLBACK IMMEDIATE;
END
GO

IF DB_ID('THITRACNGHIEM') IS NOT NULL
BEGIN
    DROP DATABASE THITRACNGHIEM;
END
GO

CREATE DATABASE THITRACNGHIEM
GO

USE THITRACNGHIEM
GO

BEGIN TRY

BEGIN TRAN

/* =========================================
                TABLE LOP
========================================= */

CREATE TABLE LOP (
    MALOP NCHAR(15) PRIMARY KEY,
    TENLOP NVARCHAR(40) NOT NULL UNIQUE
)

INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'D18CQCN01      ', N'Ngành CNTT Khóa 2018 -1')
INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'TH04           ', N'TIN HOC 2004')
INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'TH05           ', N'TIN HOC 2005')
INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'TH06           ', N'TIN HOC 2006')
INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'TH07           ', N'TIN HOC 2007')
INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'TH08           ', N'TIN HOC 2008')
INSERT [dbo].[LOP] ([MALOP], [TENLOP]) VALUES (N'VT04           ', N'VIỄN THÔNG 2004')

/* =========================================
            TABLE MONHOC
========================================= */

CREATE TABLE MONHOC (
    MAMH NCHAR(5) PRIMARY KEY,
    TENMH NVARCHAR(40) NOT NULL UNIQUE,
    IS_DELETED BIT NOT NULL DEFAULT 0
)

INSERT [dbo].[MONHOC] ([MAMH], [TENMH]) VALUES (N'AVCB ', N'ANH VĂN CĂN BẢN')
INSERT [dbo].[MONHOC] ([MAMH], [TENMH]) VALUES (N'CTDL ', N'CẤU TRÚC DỮ LIỆU')
INSERT [dbo].[MONHOC] ([MAMH], [TENMH]) VALUES (N'CSDL ', N'CƠ SỞ DỮ LIỆU')
INSERT [dbo].[MONHOC] ([MAMH], [TENMH]) VALUES (N'MMTCB', N'MẠNG MÁY TÍNH CĂN BẢN')

/* =========================================
            TABLE SINHVIEN
========================================= */

CREATE TABLE SINHVIEN (
    MASV NCHAR(8) PRIMARY KEY,
    HO NVARCHAR(40),
    TEN NVARCHAR(10),
    NGAYSINH DATE,
    DIACHI NVARCHAR(100),
    IS_DELETED BIT NOT NULL DEFAULT 0,    EMAIL NVARCHAR(150),
    MALOP NCHAR(15) NOT NULL
);

INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'001     ', N'LÊ VĂN ', N'THÀNH', '1985-03-06', N'23/5 PHUNG KHAC KHOAN F3 Q3', N'bao0908235279@gmail.com', N'TH04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'002     ', N'DAO TRONG', N'KHAI', '1979-08-19', N'15/72 LE VAN THO F8 GOVAP', N'002@student.edu.vn', N'TH04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'003     ', N'CAO TUAN', N'KHA', '1985-12-06', N'12/5 LE QUANG DINH F5 GO VAP', N'003@student.edu.vn', N'TH04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'004     ', N'HA THANH ', N'BINH', '1984-03-24', N'23/4 HOANG HOA THAM', N'004@student.edu.vn', N'TH04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'005     ', N'NGUYEN THÚY ', N'VÂN', '1987-11-06', N'7 HUYNH THUC KHANG', N'005@student.edu.vn', N'TH05           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'006     ', N'NGUYEN NGOC ', N'YEN', '1980-11-23', N'3/5 AN DUONG VUONG', N'006@student.edu.vn', N'TH05           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'007     ', N'NGUYEN THUY ', N'DUNG', '1988-05-23', N'8 HUYNH VAN BANH F1 Q BINH THANH', N'007@student.edu.vn', N'TH05           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'008     ', N'TRINH', N'PHONG', '1985-12-10', N'', N'008@student.edu.vn', N'TH06           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'009     ', N'TRAN THANH', N'HUNG', '1985-03-28', N'', N'009@student.edu.vn', N'TH05           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'011     ', N'PHAN HONG', N'NGOC', '1986-01-17', N'PHAN VAN HAN BINH THANH', N'011@student.edu.vn', N'TH05           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'012     ', N'NGUYEN DUC', N'MINH', '2001-02-14', N'12 Le Loi, Thu Duc', N'012@student.edu.vn', N'TH04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'013     ', N'TRAN NGOC', N'ANH', '2001-06-18', N'45 Pham Van Dong, Thu Duc', N'013@student.edu.vn', N'TH06           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'014     ', N'LE HOANG', N'LONG', '2001-09-03', N'77 Dang Van Bi, Thu Duc', N'014@student.edu.vn', N'TH06           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'015     ', N'PHAM THU', N'TRANG', '2001-12-21', N'18 Vo Van Ngan, Thu Duc', N'015@student.edu.vn', N'TH06           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'016     ', N'BUI GIA', N'HUY', '2001-04-11', N'22 Kha Van Can, Thu Duc', N'016@student.edu.vn', N'TH06           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'017     ', N'VO MINH', N'KHOA', '2002-01-15', N'99 Nguyen Oanh, Go Vap', N'017@student.edu.vn', N'TH07           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'018     ', N'DANG THI', N'MY', '2002-03-09', N'15 Quang Trung, Go Vap', N'018@student.edu.vn', N'TH07           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'019     ', N'NGO TUAN', N'KIET', '2002-05-30', N'201 Le Duc Tho, Go Vap', N'019@student.edu.vn', N'TH07           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'020     ', N'PHAN GIA', N'BAO', '2002-07-17', N'61 Duong Quang Ham, Go Vap', N'020@student.edu.vn', N'TH07           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'021     ', N'HOANG NGOC', N'HA', '2002-11-02', N'88 Nguyen Kiem, Go Vap', N'021@student.edu.vn', N'TH07           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'022     ', N'LY KHANH', N'CHI', '2002-02-05', N'13 Nguyen Son, Tan Phu', N'022@student.edu.vn', N'TH08           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'023     ', N'TRINH QUOC', N'DAT', '2002-04-28', N'52 Au Co, Tan Phu', N'023@student.edu.vn', N'TH08           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'024     ', N'NGUYEN PHU', N'LOC', '2002-06-12', N'121 Luy Ban Bich, Tan Phu', N'024@student.edu.vn', N'TH08           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'025     ', N'LAM THI', N'YEN', '2002-08-19', N'73 Truong Chinh, Tan Phu', N'025@student.edu.vn', N'TH08           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'026     ', N'PHAM HUU', N'PHUC', '2002-10-25', N'144 Tan Huong, Tan Phu', N'026@student.edu.vn', N'TH08           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'027     ', N'LE TAN', N'TAI', '2001-01-08', N'17 Nguyen Van Linh, Quan 7', N'027@student.edu.vn', N'VT04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'028     ', N'NGUYEN THI', N'DAO', '2001-03-27', N'31 Huynh Tan Phat, Quan 7', N'028@student.edu.vn', N'VT04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'029     ', N'TRAN QUOC', N'VINH', '2001-05-16', N'102 Tran Xuan Soan, Quan 7', N'029@student.edu.vn', N'VT04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'030     ', N'BUI THAO', N'VY', '2001-08-06', N'56 Nguyen Thi Thap, Quan 7', N'030@student.edu.vn', N'VT04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'031     ', N'VO NGOC', N'THI', '2001-11-13', N'89 Tan My, Quan 7', N'031@student.edu.vn', N'VT04           ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'032     ', N'NGUYEN KHAC', N'DUY', '2000-02-22', N'11 To Huu, Thu Duc', N'032@student.edu.vn', N'D18CQCN01      ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'033     ', N'PHAM MY', N'LINH', '2000-04-14', N'25 Xa Lo Ha Noi, Thu Duc', N'033@student.edu.vn', N'D18CQCN01      ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'034     ', N'TRAN HUU', N'NHAN', '2000-07-29', N'64 Vo Chi Cong, Thu Duc', N'034@student.edu.vn', N'D18CQCN01      ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'035     ', N'LE THI', N'THUY', '2000-09-10', N'90 Do Xuan Hop, Thu Duc', N'035@student.edu.vn', N'D18CQCN01      ');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, EMAIL, MALOP) VALUES (N'036     ', N'NGO ANH', N'QUAN', '2000-12-05', N'43 Nguyen Duy Trinh, Thu Duc', N'036@student.edu.vn', N'D18CQCN01      ');

/* =========================================
            TABLE GIAOVIEN
========================================= */

CREATE TABLE GIAOVIEN (
    MAGV NCHAR(8) PRIMARY KEY,
    HO NVARCHAR(40),
    TEN NVARCHAR(10),
    SODTLL NCHAR(15),
    DIACHI NVARCHAR(50),
    IS_DELETED BIT NOT NULL DEFAULT 0,
    EMAIL NVARCHAR(150)
)
INSERT [dbo].[GIAOVIEN] ([MAGV], [HO], [TEN], [SODTLL], [DIACHI], [EMAIL]) VALUES (N'TH101   ', N'KIEU DAC', N'THIEN', N'0901234567', N'9,3A, Q.BINH TAN', N'th101@teacher.edu.vn');

INSERT [dbo].[GIAOVIEN] ([MAGV], [HO], [TEN], [SODTLL], [DIACHI], [EMAIL]) VALUES (N'TH123   ', N'PHAN VAN ', N'HAI', N'0912345678', N'15/72 LE VAN THO F8 GO VAP', N'th123@teacher.edu.vn');

INSERT [dbo].[GIAOVIEN] ([MAGV], [HO], [TEN], [SODTLL], [DIACHI], [EMAIL]) VALUES (N'TH234   ', N'DAO VAN ', N'TUYET', N'0923456789', N'14/7 BUI DINH TUY TAN BINH', N'th234@teacher.edu.vn');

INSERT [dbo].[GIAOVIEN] ([MAGV], [HO], [TEN], [SODTLL], [DIACHI], [EMAIL]) VALUES (N'TH657   ', N'PHAN HONG', N'NGOC', N'0934567890', N'', N'th657@teacher.edu.vn');
/* =========================================
            TABLE TAIKHOAN
========================================= */

CREATE TABLE TAIKHOAN (
    MA NCHAR(8) PRIMARY KEY,

    PASSWORD_HASH VARCHAR(255) NOT NULL,

    ROLE VARCHAR(20) NOT NULL
        CHECK (ROLE IN ('PGV', 'GIAOVIEN', 'SINHVIEN'))
);
CREATE TABLE PASSWORD_RESET_OTP (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    ACCOUNT_ID NCHAR(8) NOT NULL,
    EMAIL NVARCHAR(150) NOT NULL,
    PURPOSE NVARCHAR(30) NOT NULL,
    OTP_CODE CHAR(6) NOT NULL,
    EXPIRES_AT DATETIME2 NOT NULL,
    USED BIT NOT NULL DEFAULT 0,
    USED_AT DATETIME2 NULL,
    CREATED_AT DATETIME2 NOT NULL DEFAULT SYSDATETIME()
);

CREATE INDEX IX_PASSWORD_RESET_OTP_ACCOUNT
ON PASSWORD_RESET_OTP (ACCOUNT_ID);

CREATE INDEX IX_PASSWORD_RESET_OTP_CODE
ON PASSWORD_RESET_OTP (OTP_CODE);

CREATE INDEX IX_PASSWORD_RESET_OTP_EXPIRES_AT
ON PASSWORD_RESET_OTP (EXPIRES_AT);

INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('PGV00001', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'PGV');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('001', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('002', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('003', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('004', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('005', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('006', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('007', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('008', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('009', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('011', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('TH101', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'GIAOVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('TH123', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'GIAOVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('TH234', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'GIAOVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('TH657', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'GIAOVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('012', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('013', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('014', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('015', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('016', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('017', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('018', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('019', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('020', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('021', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('022', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('023', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('024', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('025', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('026', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('027', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('028', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('029', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('030', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('031', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('032', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('033', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('034', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('035', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');
INSERT INTO TAIKHOAN (MA, PASSWORD_HASH, ROLE) VALUES ('036', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SINHVIEN');

INSERT INTO PASSWORD_RESET_OTP (ACCOUNT_ID, EMAIL, PURPOSE, OTP_CODE, EXPIRES_AT, USED, USED_AT, CREATED_AT)
VALUES
    (N'001     ', N'bao0908235279@gmail.com', N'PASSWORD_RESET', '135790', DATEADD(MINUTE, 5, SYSDATETIME()), 0, NULL, SYSDATETIME()),
    (N'TH123   ', N'th123@teacher.edu.vn', N'EMAIL_CHANGE', '246810', DATEADD(MINUTE, -10, SYSDATETIME()), 1, DATEADD(MINUTE, -7, SYSDATETIME()), DATEADD(MINUTE, -15, SYSDATETIME()));
/* =========================================
        TABLE GIAOVIEN_DANGKY
========================================= */

CREATE TABLE GIAOVIEN_DANGKY (
    MAGV NCHAR(8) NOT NULL,

    MALOP NCHAR(15) NOT NULL,

    MAMH NCHAR(5) NOT NULL,

    TRINHDO NCHAR(1)
        CHECK (TRINHDO IN ('A', 'B', 'C')),

    NGAYTHI DATETIME DEFAULT GETDATE(),

    LAN SMALLINT
        CHECK (LAN BETWEEN 1 AND 2),

    SOCAUTHI SMALLINT
        CHECK (SOCAUTHI BETWEEN 10 AND 100),

    THOIGIAN SMALLINT
        CHECK (THOIGIAN BETWEEN 5 AND 60),

    CONSTRAINT PK_GVDK
        PRIMARY KEY (MALOP, MAMH, LAN)
)

/* =========================================
                TABLE BODE
========================================= */

CREATE TABLE BODE (
    CAUHOI INT IDENTITY(1,1) PRIMARY KEY,

    MAMH NCHAR(5) NOT NULL,

    TRINHDO CHAR(1)
        CHECK (TRINHDO IN ('A', 'B', 'C')),

    NOIDUNG NVARCHAR(200),

    A NVARCHAR(250),
    B NVARCHAR(250),
    C NVARCHAR(250),
    D NVARCHAR(250),

    DAP_AN NCHAR(1)
        CHECK (DAP_AN IN ('A', 'B', 'C', 'D')),

    MAGV NCHAR(8) NOT NULL,
    IS_DELETED BIT NOT NULL DEFAULT 0
)

/* =========================================
            TABLE CT_GIAOVIEN_DANGKY
========================================= */

CREATE TABLE CT_GIAOVIEN_DANGKY (
    MALOP nchar(15),
    MAMH nchar(5),
    LAN smallint,
    CAUHOI int,
    PRIMARY KEY (MALOP, MAMH, LAN, CAUHOI),
    FOREIGN KEY (MALOP, MAMH, LAN) REFERENCES GIAOVIEN_DANGKY(MALOP, MAMH, LAN),
    FOREIGN KEY (CAUHOI) REFERENCES BODE(CAUHOI)
);

SET IDENTITY_INSERT [dbo].[BODE] ON


INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (1, N'MMTCB', N'A', N'mạng máy tính(compute netword) so với hệ thống tập trung multi-user', N'dễ phát triển hệ thống', N'tăng độ tin cậy', N'tiết kiệm chi phí', N'tất cả đều đúng', N'D', N'TH657   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (3, N'MMTCB', N'A', N'để một máy tính truyền dữ liệu cho một số máy khác trong mạng, ta dùng loại địa chỉ', N'Broadcast', N'Broadband', N'multicast', N'multiple access', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (4, N'MMTCB', N'A', N'thứ tự phân loại mạng theo chiều dài đường truyền', N'internet, lan, man, wan', N'internet, wan, man, lan', N'lan, wan, man, internet', N'man, lan, wan, internet', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (5, N'MMTCB', N'A', N'mạng man được sử dụng trong phạm vi:', N'quốc gia', N'lục địa', N'khu phố', N'thành phố', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (6, N'MMTCB', N'A', N'thuật ngữ man được viết tắt bởi:', N'middle area network', N'metropolitan area network', N'medium area network', N'multiple access network', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (7, N'MMTCB', N'A', N'mạng man không kết nối theo sơ đồ:', N'bus', N'ring', N'star', N'tree', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (8, N'MMTCB', N'A', N'kiến trúc mạng (network architechture) là:', N'tập các chức năng trong mạng', N'tập các cấp và các protocol trong mỗi cấp', N'tập các dịch vụ trong mạng', N'tập các protocol trong mạng', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (9, N'MMTCB', N'A', N'thuật ngữ nào không cùng nhóm:', N'simplex', N'multiplex', N'half duplex', N'full duplex', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (10, N'MMTCB', N'A', N'loại dịch vụ nào có thể nhận dữ liệu không đúng thứ tự khi truyền', N'point to point', N'có kết nối', N'không kết nối', N'broadcast', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (11, N'MMTCB', N'A', N'dịch vụ không xác nhận (unconfirmed) chỉ sử dụng 2 phép toán cơ bản:', N'response and confirm', N'confirm and request', N'request and indication', N'indication and response', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (12, N'MMTCB', N'A', N'Chọn câu sai trong các nguyên lý phân cấp của mô hình OSI', N'Mỗi cấp thực hiện 1 chức năng rõ ràng', N'Mỗi cấp được chọn sao cho thông tin trao đổi giữa các cấp tối thiểu', N'Mỗi cấp được tạo ra ứng với 1 mức trừu tượng hóa', N'Mỗi cấp phải cung cấp cùng 1 kiểu địa chỉ và dịch vụ', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (13, N'MMTCB', N'A', N'Chức năng của cấp vật lý(physical)', N'Qui định truyền 1 hay 2 chiều', N'Quản lý lỗi sai', N'Xác định thời gian truyền 1 bit dữ liệu', N'Quản lý địa chỉ vật lý', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (14, N'MMTCB', N'A', N'Chức năng câp liên kết dữ liệu (data link)', N'Quản lý lỗi sai', N'Mã hóa dữ liệu', N'Tìm đường đi cho dữ liệu', N'Chọn kênh truyền', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (15, N'MMTCB', N'A', N'Chức năng cấp mạng (network)', N'Quản lý lưu lượng đường truyền', N'Điều khiển hoạt động subnet', N'Nén dữ liệu', N'Chọn điện áp trên kênh truyền', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (16, N'MMTCB', N'A', N'Chức năng cấp vận tải (transport) ', N'Quản lý địa chỉ mạng', N'Chuyển đổi các dạng frame khác nhau', N'Thiết lập và hủy bỏ dữ liệu', N'Mã hóa và giải mã dữ liệu', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (17, N'MMTCB', N'A', N'Cáp xoắn đôi trong mạng LAN dùng đầu nối', N'AUI', N'BNC', N'RJ11', N'RJ45', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (18, N'MMTCB', N'A', N'T-connector dùng trong loại cáp', N'10Base-2', N'10Base-5', N'10Base-T', N'10Base-F', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (19, N'MMTCB', N'A', N'chọn câu sai trong các nguyên lý phân cấp của mô hình osi', N'mỗi cấp thực hiện 1 chức năng rõ ràng', N'mỗi cấp được chọn sao cho thông tin trao đổi giữa các cấp tối thiểu', N'mỗi cấp được tạo ra ứng với 1 mức trừu tượng hóa', N'mỗi cấp phải cung cấp cùng một kiểu địa chỉ và dịch vụ', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (20, N'AVCB ', N'A', N'The publishers suggested that the envelopes be sent to ...... by courier so that the film can be developed as soon as possible', N'they', N'their', N'theirs', N'them', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (21, N'AVCB ', N'A', N'Board members ..... carefully define their goals and objectives for the agency before the monthly meeting next week.', N'had', N'should', N'used ', N'have', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (22, N'AVCB ', N'A', N'For business relations to continue between our two firms, satisfactory agreement must be ...... reached and signer', N'yet', N'both', N'either ', N'as well as', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (23, N'AVCB ', N'A', N'The corporation, which underwent a major restructing seven years ago, has been growing steadily ......five years', N'for', N'on', N'from', N'since', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (24, N'AVCB ', N'A', N'Making advance arrangements for audiovisual equipment is....... recommended for all seminars.', N'sternly', N'strikingly', N'stringently', N'strongly', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (25, N'AVCB ', N'A', N'Two assistants will be required to ...... reporter''s names when they arrive at the press conference', N'remark', N'check', N'notify', N'ensure', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (26, N'AVCB ', N'A', N'The present government has an excellent ......to increase exports', N'popularity', N'regularity', N'celebrity', N'opportunity', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (27, N'AVCB ', N'A', N'While you are in the building, please wear your identification badge at all times so that you are ....... as a company employee.', N'recognize', N'recognizing', N'recognizable', N'recognizably', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (28, N'AVCB ', N'A', N'Our studies show that increases in worker productivity have not been adequately .......rewarded by significant increases in ......', N'compensation', N'commodity', N'compilation', N'complacency', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (29, N'AVCB ', N'A', N'Conservatives predict that government finaces will remain...... during the period of the investigation', N'authoritative', N'summarized', N'examined', N'stable', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (30, N'AVCB ', N'B', N'Battery-operated reading lamps......very well right now', N'sale', N'sold', N'are selling', N'were sold', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (31, N'AVCB ', N'B', N'In order to place a call outside the office, you have to .......nine first. ', N'tip', N'make', N'dial', N'number', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (32, N'AVCB ', N'B', N'We are pleased to inform...... that the missing order has been found.', N'you', N'your', N'yours', N'yourseld', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (33, N'AVCB ', N'B', N'Unfortunately, neither Mr.Sachs....... Ms Flynn will be able to attend the awards banquet this evening', N'but', N'and', N' nor', N'either', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (34, N'AVCB ', N'B', N'According to the manufacturer, the new generatir is capable of....... the amount of power consumed by our facility by nearly ten percent.', N'reduced', N'reducing', N'reduce', N'reduces', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (35, N'AVCB ', N'B', N'After the main course, choose from our wide....... of homemade deserts', N'varied', N'various', N'vary', N'variety', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (36, N'AVCB ', N'B', N'One of the most frequent complaints among airline passengers is that there is not ...... legroom', N'enough', N'many', N'very', N'plenty', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (37, N'AVCB ', N'B', N'Faculty members are planning to..... a party in honor of Dr.Walker, who will retire at the end of the semester', N'carry', N'do', N'hold', N'take', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (38, N'AVCB ', N'B', N'Many employees seem more ....... now about how to use the new telephone system than they did before they attended the workshop', N'confusion', N'confuse', N'confused', N'confusing', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (39, N'AVCB ', N'B', N'.........our production figures improve in the near future, we foresee having to hire more people between now and July', N'During', N'Only', N'Unless', N'Because', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (40, N'AVCB ', N'C', N'Though their performance was relatively unpolished, the actors held the audience''s ........for the duration of the play.', N'attentive', N'attentively', N'attention', N'attentiveness', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (41, N'AVCB ', N'C', N'Dr. Abernathy''s donation to Owstion College broke the record for the largest private gift...... give to the campus', N'always', N'rarely', N'once', N'ever', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (42, N'AVCB ', N'C', N'Savat Nation Park is ....... by train,bus, charter plane, and rental car.', N'accessible', N'accessing', N'accessibility', N'accesses', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (43, N'AVCB ', N'C', N'In Piazzo''s lastest architectural project, he hopes to......his flare for blending contemporary and traditional ideals.', N'demonstrate', N'appear', N'valve', N'position', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (44, N'AVCB ', N'C', N'Replacing the offic equipment that the company purchased only three years ago seems quite.....', N'waste', N'wasteful', N'wasting', N'wasted', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (45, N'AVCB ', N'C', N'On........, employees reach their peak performance level when they have been on the job for at least two years.', N'common', N'standard', N'average', N'general', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (46, N'AVCB ', N'C', N'We were........unaware of the problems with the air-conidtioning units in the hotel rooms until this week.', N'complete ', N'completely', N'completed', N'completing', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (47, N'AVCB ', N'C', N'If you send in an order ....... mail, we recommend that you phone our sales division directly to confirm the order.', N'near', N'by', N'for', N'on', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (48, N'AVCB ', N'C', N'A recent global survey suggests.......... demand for aluminum and tin will remain at its current level for the next five to ten years.', N'which', N'it ', N'that', N'both', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (49, N'AVCB ', N'C', N'Rates for the use of recreational facilities do not include ta and are subject to change without.........', N'signal', N'cash', N'report', N'notice', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (50, N'AVCB ', N'A', N'Aswering telephone calls is the..... of an operator', N'responsible', N'responsibly', N'responsive', N'responsibility', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (51, N'AVCB ', N'A', N'A free watch will be provided with every purchase of $20.00 or more a ........ period of time', N'limit', N'limits', N'limited', N'limiting', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (52, N'AVCB ', N'A', N'The president of the corporation has .......arrived in Copenhagen and will meet with the Minister of Trade on Monday morning', N'still', N'yet', N'already', N'soon', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (53, N'AVCB ', N'A', N'Because we value your business, we have .......for card members like you to receive one thousand  dollars of complimentary life insurance', N'arrange', N'arranged', N'arranges', N'arranging', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (54, N'AVCB ', N'A', N'Employees are........that due to the new government regulations. there is to be no smoking in the factory', N'reminded', N'respected', N'remembered', N'reacted', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (55, N'AVCB ', N'A', N'MS. Galera gave a long...... in honor of the retiring vice-president', N'speak', N'speaker', N'speaking', N'speech', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (56, N'AVCB ', N'A', N'Any person who is........ in volunteering his or her time for the campaign should send this office a letter of intent', N'interest', N'interested', N'interesting', N'interestingly', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (57, N'AVCB ', N'A', N'Mr.Gonzales was very concerned.........the upcoming board of directors meeting', N'to', N'about', N'at ', N'upon', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (58, N'AVCB ', N'A', N'The customers were told that no ........could be made on weekend nights because the restaurant was too busy', N'delays', N'cuisines', N'reservation', N'violations', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (59, N'AVCB ', N'A', N'The sales representive''s presentation was difficult to understand ........ he spoke very quickly', N'because', N'althought', N'so that', N'than', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (60, N'AVCB ', N'B', N'It has been predicted that an.......weak dollar will stimulate tourism in the United States', N'increased', N'increasingly', N'increases', N'increase', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (61, N'AVCB ', N'B', N'The firm is not liable for damage resulting from circumstances.........its control.', N'beyond', N'above', N'inside', N'around', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (62, N'AVCB ', N'B', N'Because of.......weather conditions, California has an advantage in the production of fruits and vegetables', N'favorite', N'favor', N'favorable', N'favorably', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (63, N'AVCB ', N'B', N'On international shipments, all duties and taxes are paid by the..........', N'recipient', N'receiving', N'receipt', N'receptive', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (64, N'AVCB ', N'B', N'Although the textbook gives a definitive answer,wise managers will look for........ own creative solutions', N'them', N'their', N'theirs', N'they', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (65, N'AVCB ', N'B', N'Initial ....... regarding the merger of the companies took place yesterday at the Plaza Conference Center.', N'negotiations', N'dedications', N'propositions', N'announcements', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (66, N'AVCB ', N'B', N'Please......... photocopies of all relevant docunments to this office ten days prior to your performance review date', N'emerge', N'substantiate', N'adapt', N'submit', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (67, N'AVCB ', N'B', N'The auditor''s results for the five year period under study were .........the accountant''s', N'same', N'same as', N'the same', N'the same as', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (68, N'AVCB ', N'B', N'.........has the marketing environment been more complex and subject to change', N'Totally', N'Negatively', N'Decidedly', N'Rarely', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (69, N'AVCB ', N'B', N'All full-time staff are eligible to participate in the revised health plan, which becomes effective the first ......... the month.', N'of', N'to', N'from', N'for', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (70, N'AVCB ', N'C', N'Contracts must be read........ before they are signed.', N'thoroughness', N'more thorough', N'thorough', N'thoroughly', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (71, N'AVCB ', N'C', N'Passengers should allow for...... travel time to the airport in rush hour traffic', N'addition', N'additive', N'additionally', N'additional', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (72, N'AVCB ', N'C', N'This fiscal year, the engineering team has worked well together on all phases ofproject.........', N'development', N'developed', N'develops', N'developer', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (73, N'AVCB ', N'C', N'Mr.Dupont had no ....... how long it would take to drive downtown', N'knowledge', N'thought', N'idea', N'willingness', N'C', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (74, N'AVCB ', N'C', N'Small company stocks usually benefit..........the so called January effect that cause the price of these stocks to rise between November and January', N'unless', N'from', N'to ', N'since', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (75, N'AVCB ', N'C', N'It has been suggested that employees ........to work in their current positions until the quarterly review is finished.', N'continuity', N'continue', N'continuing', N'continuous', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (76, N'AVCB ', N'C', N'It is admirable that Ms.Jin wishes to handle all transactions by........, but it might be better if several people shared the responsibility', N'she', N'herself', N'her', N'hers', N'B', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (77, N'AVCB ', N'C', N'This new highway construction project will help the company.........', N'diversity', N'clarify', N'intensify', N'modify', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (78, N'AVCB ', N'C', N'Ms.Patel has handed in an ........business plan to the director', N'anxious', N'evident', N'eager', N'outstanding', N'D', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (79, N'AVCB ', N'C', N'Recent changes in heating oil costs have affected..........production of turniture', N'local', N'locality', N'locally', N'location', N'A', N'TH234   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (80, N'MMTCB', N'A', N'Termiator là linh kiện dùng trong loại cáp mạng', N'Cáp quang', N'UTP và STP ', N'Xoắn đôi', N'Đồng trục', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (81, N'MMTCB', N'A', N'Mạng không dây dùng loại sóng nào không bị ảnh hưởng bởi khoảng cách địa lý', N'Sóng radio', N'Sống hồng ngoại', N'Sóng viba', N'Song cực ngắn', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (82, N'MMTCB', N'A', N'Đường truyền E1 gồm 32 kênh, trong đó sử dụng cho dữ liệu là:', N'32 kênh', N'31 kênh', N'30 kênh', N'24 kênh', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (83, N'MMTCB', N'A', N'Mạng máy tính thường sử dụng loại chuyển mach', N'Gói (packet switch)', N'Kênh (Circuit switch)', N'Thông báo(message switch)', N'Tất cả đều đúng', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (84, N'MMTCB', N'A', N'Cáp UTP hỗ trợ tôc độ truyền 100MBps là loại', N'Cat 3', N'Cat 4', N'Cat 5', N'Cat 6', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (85, N'MMTCB', N'A', N'Thiết bị nào làm việc trong cấp vật lý (physical) ', N'Terminator', N'Hub', N'Repeater', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (86, N'MMTCB', N'A', N'Phương pháp dồn kênh phân chia tần số gọi là', N'FDM', N'WDM', N'TDM', N'CSMA', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (87, N'MMTCB', N'A', N'Dịch vụ nào không sử dụng trong cấp data link', N'Xác nhận, có kết nối', N'Xác nhận, không kết nôi', N'Không xác nhận, có kết nối', N'Không xác nhận, không kết nối', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (88, N'MMTCB', N'A', N'Nguyên nhân gây sai sót khi gửi/nhận dữ liệu trên mạng', N'Mất đồng bộ trong khi truyền', N'Nhiễu từ môi trường', N'Lỗi phần cứng hoặc phần mềm', N'Tất cả đều đúng ', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (89, N'MMTCB', N'A', N'Để tránh sai sót khi truyền dữ liệu trong cấp data link', N'Đánh số thứ tự frame', N'Quản lý dữ liệu theo frame', N'Dùng vùng checksum', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (90, N'MMTCB', N'A', N'Quản lý lưu lượng đường truyền là chức năng của cấp', N'Presentation', N'Network', N'Data link', N'Physical', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (91, N'MMTCB', N'A', N'Hoạt động của protocol Stop and Wait', N'Chờ một khoảng thời gian time-out rồi gửi tiếp frame kế', N'Chờ 1 khoảng thời gian time-out rồi gửi lại frame trước', N'Chờ nhận được ACK của frame trước mới gửi tiếp frame kế', N'Không chờ mà gửi liên tiếp các frame kế nhau', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (92, N'MMTCB', N'A', N'Protocol nào tạo frame bằng phương pháp chèn kí tự', N'ADCCP', N'HDLC', N'SDLC', N'PPP', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (93, N'MMTCB', N'A', N'Phương pháp nào được dủng trong việc phát hiện lỗi', N'Timer', N'Ack', N'Checksum', N'Tất cả đều đúng', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (94, N'MMTCB', N'A', N'Kiểm soát lưu lượng (flow control) có nghĩa là', N'Thay đổi thứ tự truyền frame', N'Điều tiết tốc độ truyền frame', N'Thay đổi thời gian chờ time-out', N'Điều chỉnh kích thước frame', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (95, N'MMTCB', N'A', N'Khả năng nhận biết tình trạng đường truyền ( carrier sence) là', N'Xác định đường truyền tốt hay xấu', N'Có kết nối được hay không', N'Nhận biết có xung đột hay không', N'Đường truyền đang rảnh hay bận', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (96, N'MMTCB', N'A', N'Mạng nào không có khả năng nhận biết tình trạng đường truyền (carrier sence)', N'ALOHA', N'CSMA', N'CSMA/CD', N'Tất cả đều đúng ', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (97, N'MMTCB', N'A', N'Mạng nào có khả năng nhận biết xung đột (collision)', N'ALOHA', N'CSMA', N'CSMA/CD', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (98, N'MMTCB', N'A', N'Chuẩn mạng nào có khả năng pkhát hiện xung đột (collision) trong khi truyền', N'1-persistent CSMA', N'p-persistent CSMA', N'Non-persistent CSMA', N'CSMA/CD', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (99, N'MMTCB', N'A', N'Loại mạng cục bộ nào dùng chuẩn CSMA/CD', N'Token-ring', N'Token-bus', N'Ethernet', N'ArcNet', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (100, N'MMTCB', N'A', N'Mạng Ethernet được IEEE đưa vào chuẩn', N'IEEE 802.2', N'IEEE 802.3', N'IEEE 802.4', N'IEEE 802.5', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (101, N'MMTCB', N'A', N'Chuẩn nào không dùng trong mạng cục bộ (LAN )', N'IEEE 802.3', N'IEEE 802.4', N'IEEE 802.5', N'IEEE 802.6', N'D', N'TH123   ')

INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (102, N'MMTCB', N'A', N'Loại mạng nào dùng 1 máy tính làm Monitor để bảo trì mạng', N'Ethernet', N'Token-ring', N'Token-bus', N'Tất cả đều sai', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (103, N'MMTCB', N'A', N'Loại mạng nào không có độ ưu tiên', N'Ethernet', N'Token-ring', N'Token-bus', N'Tất cả đều sai', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (104, N'MMTCB', N'A', N'Loại mạng nào dùng 2 loại frame khác nhau trên đường truyền', N'Token-ring', N'Token-bus', N'Ethernet', N'Tất cả đều sai', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (105, N'MMTCB', N'A', N'Vùng dữ liệu trong mạng Ethernet chứa tối đa', N'185 bytes', N'1500 bytes', N'8182 bytes', N'Không giới hạn', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (106, N'MMTCB', N'A', N'Chọn câu sai:" Cầu nối (bridge) có thể kết nối các mạng có...."', N'Chiều dài frame khác nhau', N'Cấu trúc frame khác nhau', N'Tốc độ truyền khác nhau', N'Chuẩn khác nhau', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (107, N'MMTCB', N'A', N'Mạng nào có tốc độ truyền lớn hơn 100Mbps', N'Fast Ethernet', N'Gigabit Ethernet', N'Ethernet', N'Tất cả đều đúng', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (108, N'MMTCB', N'A', N'Mạng Ethernet sử dụng được loại cáp', N'Cáp quang', N'Xoắn đôi', N'Đồng trục', N'Tất cả đều đúgn', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (109, N'MMTCB', N'A', N'Khoảng cách đường truyền tối đa mạng FDDI có thể đạt', N'1Km', N'10Km', N'100Km', N'1000Km', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (110, N'MMTCB', N'A', N'Cấp network truyền nhận theo kiểu end-to-end vì nó quản lý dữ liệu', N'Giữa 2 đầu subnet', N'Giữa 2 máy tính trong mạng', N'Giữa 2 thiết bị trên đường truyền', N'Giữa 2 đầu đường truyền', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (111, N'MMTCB', N'A', N'Kiểu mạch ảo(virtual circuit) được dùng trong loại dịch vụ mạng', N'Có kết nối', N'Không kết nối', N'Truyền 1 chiều', N'Truyền 2 chiều', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (112, N'MMTCB', N'A', N'Kiểu datagram trong cấp network', N'Chỉ tìm đường 1 lần khi tạo kết nối', N'Phải tìm đường riêng cho từng packet', N'THông tin có sẵn trong packet, không cần tìm đường', N'Thông tin có sẵn trong router , không cần tìm đường', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (113, N'MMTCB', N'A', N'Kiểm soát tắc nghẽn (congestion) là nhiệm vụ của cấp', N'Physical', N'Transport', N'Data link', N'Network', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (114, N'MMTCB', N'A', N'Nguyên nhân dẫn đến tắt nghẻn (congestion) trên mạng', N'Tốc độ xử lý của router chậm', N'Buffers trong router nhỏ', N'Router có nhiều đường vào nhưng ít đường ra', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (115, N'MMTCB', N'A', N'Cấp appliation trong mô hình TCP/IP tương đương với cấp nào trong mô hình OSI', N'Session', N'Application', N'Presentation', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (116, N'MMTCB', N'A', N'Cấp nào trong mô hình mạng OSI tương đương với cấp Internet trong mô hình TCP/IP ', N'Network', N'Transport', N'Physical', N'Data link', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (117, N'MMTCB', N'A', N'Chất lượng dịch vụ mạng không được đánh giá trên chỉ tiêu nào?', N'Thời gian thiết lập kết nối ngắn', N'Tỉ lệ sai sót rất nhỏ', N'Tốc độ đường truyền cao', N'Khả năng phục hồi khi có sự cố', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (118, N'MMTCB', N'A', N'Kỹ thuật Multiplexing được dùng khi', N'Có nhiều kênh truyền hơn đường truyền', N'Có nhiều đường truyền hơn kênh truyền', N'Truyền dữ liệu số trên mạng điện thoại', N'Truyền dữ liệu tương tự trên mạng điện thọai', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (119, N'MMTCB', N'A', N'Dịch vụ truyền Email sử dụng protocol nào?', N'HTTP', N'NNTP', N'SMTP', N'FTP', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (120, N'MMTCB', N'A', N'Địa chỉ IP lớp B nằm trong phạm vi nào', N'192.0.0.0 - 223.0.0.0', N'127.0.0.0 - 191.0.0.0', N'128.0.0.0 - 191.0.0.0 ', N'1.0.0.0 - 126.0.0.0', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (121, N'MMTCB', N'A', N'Subnet Mask nào sau đây chỉ cho tối đa 2 địa chỉ host', N'255.255.255.252', N'255.255.255.254', N'255.255.255.248', N'255.255.255.240', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (122, N'MMTCB', N'A', N'Thành phần nào không thuộc socket', N'Port', N'Địa chỉ IP', N'Địa chỉ cấp MAC', N'Protocol cấp Transport', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (123, N'MMTCB', N'A', N'Mục đích của Subnet Mask trong địa chỉ IP là', N'Xác định host của địa chỉ IP', N'Xác định vùng network của địa chỉ IP', N'Lấy các bit trong vùng subnet làm địa chỉ host', N'Lấy các bit trong vùng địa chỉ host làm subnet', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (124, N'MMTCB', N'A', N'Bước đầu tiên cần thực hiện để truyền dữ liệu theo ALOHA là', N'Chờ 1 thời gian ngẫu nhiên', N'Gửi tín hiệu tạo kết nối', N'Kiểm tra tình trạng đường truyền', N'Lập tức truyền dữ liệu', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (125, N'MMTCB', N'A', N'Cầu nối trong suốt hoạt động trong cấp nào', N'Data link', N'Physical', N'Network', N'Transport', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (126, N'MMTCB', N'A', N'Tốc độ của đường truyền T1 là:', N'2048 Mbps', N'1544 Mbps', N'155 Mbps', N'56 Kbps', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (127, N'MMTCB', N'A', N'Khi một dịch vụ trả lời ACK cho biết dữ liệu đã nhận được, đó là', N'Dịch vụ có xác nhận', N'Dịch vụ không xác nhận', N'Dịch vụ có kết nối', N'Dịch vụ không kết nối', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (128, N'MMTCB', N'A', N'Loại frame nào được sử dụng trong mạng Token-ring', N'Monitor', N'Token', N'Data', N'Token và Data', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (129, N'MMTCB', N'A', N'Thuật ngữ OSI là viết tắt bởi', N'Organization for Standard Institude', N'Organization for Standard Internet', N'Open Standard Institude', N'Open System Interconnection', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (130, N'MMTCB', N'A', N'Trong mạng Token-ting, khi 1 máy nhận được Token', N'Nó phải truyền cho máy kế trong vòng', N'Nó được quyền truyền dữ liệu', N'Nó được quyền giữ lại Token', N'Tất cả đều sai', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (131, N'MMTCB', N'A', N'Trong mạng cục bộ, để xác định 1 máy trong mạng ta dùng địa chỉ', N'MAC', N'Socket', N'Domain', N'Port', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (132, N'MMTCB', N'A', N'Thứ tự các cấp trong mô hình OSI', N'Application,Session,Transport,Physical', N'Application, Transport, Network, Physical', N'Application, Presentation,Session,Network,Transport,Data link,Physical', N'Application,Presentation,Session,Transport,Network,Data link,Physical', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (133, N'MMTCB', N'A', N'Cấp vật lý (physical) không quản lý', N'Mức điện áp', N'Địa chỉ vật lý', N'Mạch giao tiếp vật lý', N'Truyền các bit dữ liêu', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (134, N'MMTCB', N'A', N'TCP sử dụng loại dịch vụ', N'Có kết nối, độ tin cậy cao', N'Có kết nối, độ tin cậy thấp', N'Không kết nối, độ tin cậy cao', N'Không kết nối, độ tin cậy thấp', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (135, N'MMTCB', N'A', N'Địa chỉ IP bao gồm', N'Địa chỉ Network và địa chỉ host', N'Địa chỉ physical và địa chỉ logical', N'Địa chỉ cấp MAC và và địa chỉ LLC', N'Địa chỉ hardware và địa chỉ software', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (136, N'MMTCB', N'A', N'Chức năng cấp mạng (network) là', N'Mã hóa và định dạng dữ liệu', N'Tìm đường và kiểm soát tắc nghẽn', N'Truy cập môi trường mạng', N'Kiểm soát lỗi và kiểm soát lưu lượng', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (137, N'MMTCB', N'A', N'Mạng CSMA/CD làm gì', N'Truyền Token trên mạng hình sao', N'Truyền Token trên mạng dạng Bus', N'Chia packet ra thành từng frame nhỏ và truỵền đi trên mạng', N'Truy cập đường truyền và truyền lại dữ liệu nếu xảy ra đụng độ', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (138, N'MMTCB', N'A', N'Tiền thân của mạng Internet là', N'Intranet', N'Ethernet', N'Arpanet', N'Token-bus', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (139, N'MMTCB', N'A', N'Khi 1 cầu nối ( bridge) nhận được 1 framechưa biết thông tin về địa chỉ máy nhận, nó sẽ', N'Xóa bỏ frame này', N'Gửi trả lại máy gốc', N'Gửi đến mọi ngõ ra còn lại', N'Giảm thời gian sống của frame đi 1 đơn vị và gửi đến mọi ngõ ra còn lại', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (140, N'MMTCB', N'A', N'Chức năng của cấp Network là', N'Tìm đường', N'Mã hóa dữ liệu', N'Tạo địa chỉ vật lý', N'Kiểm soát lưu lượng', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (141, N'MMTCB', N'B', N'Sự khác nhau giữa địa chỉ cấp Data link và Network là', N'Địa chỉ cấp Data link có kích thước nhỏ hơn địa chỉ cấp Network', N'Địa chỉ cấp Data link là đia chỉ Physic, địa chỉ cấp Network là địa chỉ Logic', N'Địa chỉ cấp Data Link là địa chỉ Logic, địa chỉ câp Network là địa chỉ Physic', N'Địa chỉ Data link cấu hình theo mạng, địa chỉ cấp Network xác định theo IEEE', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (142, N'MMTCB', N'B', N'Kỹ thuật nào không sử dụng được trong việc kiểm soát lưu lượng(flow control)', N'Ack', N'Buffer', N'Windowing', N'Multiplexing', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (143, N'MMTCB', N'B', N'Cấp cao nhất trong mô hình mạng OSI là', N'Transport', N'Physical', N'Network', N'Application', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (144, N'MMTCB', N'B', N'Tại sao mạng máy tình dùng mô hình phân cấp', N'Để mọi người sử dụng cùng 1 ứng dụng mạng', N'Để phân biệt giữa chuẩn mạng và ứng dụng mạng', N'Giảm độ phức tạp trong việc thiết kế và cài đặt', N'Các cấp khác không cần sửa đổi khi thay đổi 1 cấp mạng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (145, N'MMTCB', N'B', N'Router làm gì để giảm tăc nghẽn (congestion)', N'Nén dữ liệu', N'Lọc bớt dữ liệu theo địa chỉ vật lý', N'Lọc bớt dữ liệu theo địa chỉ logic', N'Cấm truyền dữ liệu broadcasr', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (146, N'MMTCB', N'B', N'Byte đầu của 1 IP có giá trị 222, địa chỉ này thuộc lớp địa chỉ nào', N'Lớp A', N'Lớp B', N'Lớp C', N'Lớp D', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (147, N'MMTCB', N'B', N'Chọn câu đúng đối với switch của mạng LAN', N'Là 1 cầu nối tốc độ cao', N'Nhận data từ 1 cổng và xuất ra mọi cổng còn lại', N'Nhận data từ 1 cổng và xuất ra  cổng đích tùy theo địa chỉ cấp IP', N'Nhận data từ 1 cổng và xuất ra 1 cổng đích tùy theo địa chỉ cấp MAC', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (148, N'MMTCB', N'B', N'Thuật ngữ nào cho biết loại mạng chỉ truyền được  chiều tại 1 thời điểm', N'Half duplex', N'Full duplex', N'Simplex', N'Monoplex', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (149, N'MMTCB', N'B', N'Protocol nghĩa là', N'Tập các chuẩn truyền dữ liệu', N'Tập các cấp mạng trong mô hình OSI', N'Tập các chức năng của từng cấp trong mạng', N'Tập các qui tắc và cấu trúc dữ liệu để truyền thông giữa các cấp mạng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (150, N'MMTCB', N'B', N'Truyền dữ liệu theo kiểu có kết nối không cần thực hiện việc', N'Hủy kết nối', N'Tạo kết nối', N'Truyền dữ liệu', N'Tìm đường cho từng gói tin', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (151, N'MMTCB', N'B', N'Byte đầu của địa chỉ IP lớp E nằm trong phạm vi', N'128 - 191', N'192 - 232 ', N'224 - 239 ', N'240 - 247', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (152, N'MMTCB', N'B', N'Khi truyền đi chuỗi "VIET NAM" nhưng nhận được chuỗi"MAN TEIV ". Cần phải hiệu chỉnh các protocol trong cấp nào để truyền chính xác', N'Session', N'Transport', N'Application', N'Presentation', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (153, N'MMTCB', N'B', N'Tên cáp UTP dùng torng mạng Fast Ethernet là', N'100BaseF', N'100Base2', N'100BaseT', N'100Base5', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (154, N'MMTCB', N'B', N'Tốc độ truyền của mạng Ethernet là', N'1 Mbps', N'10 Mbps', N'100 Mbps', N'1000 Mbps', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (155, N'MMTCB', N'B', N'Dịch vụ mạng thường được phân chia thành', N'Dịch vụ không kết nối và có kết nối', N'Dich vụ có xác nhận và không xác nhận', N'Dịch vụ có độ tin cậy cao và có độ tin cậy thấp', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (156, N'MMTCB', N'B', N'Đơn vị truyền dữ liệu trong cấp Network gọi là', N'Bit', N'Frame', N'Packet', N'Segment', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (157, N'MMTCB', N'B', N'Protocol nào trong mạng TCP/IP chuyển đổi địa chỉ vật lý thành địa chỉ IP', N'IP', N'ARP', N'ICMP', N'RARP', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (158, N'MMTCB', N'B', N'Đầu nới AUI dùng cho loại cáp nào?', N'Đồng trục', N'Xoắn đôi', N'Cáp quang', N'Tất cả đều đúng', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (159, N'MMTCB', N'B', N'Subnet mask chuẩn của địa chỉ IP lớp B là', N'255.0.0.0', N'255.255.0.0', N'255.255.255.0', N'255.255.255.255', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (160, N'MMTCB', N'B', N'Lý do nào khiến người ta chọn protocol TCP hơn là UDP', N'Không ACK', N'Dễ sử dụng', N'Độ tin cậy', N'Không kết nối', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (161, N'MMTCB', N'B', N'Nhược điểm của dịch vụ có kết nối so với không kết nối', N'Độ tin cậy', N'Thứ tự nhận dữ liệu không đúng', N'Đường truyền không thay đổi', N'Đường truyền thay đổi liên tục', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (162, N'MMTCB', N'B', N'Cấp Data link không thực hiện chức năng nào?', N'Kiểm soát lỗi', N'Địa chỉ vật lý', N'Kiểm soát lưu lượng', N'Thiết lập kết nối', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (163, N'MMTCB', N'B', N'Cầu nối (bridge)dựa vào thông tin nào để truyền tiếp hoặc hủy bỏ 1 frame', N'Điạ chỉ nguồn', N'Địa chỉ đích', N'Địa chỉ mạng', N'Tất cả đều đúng', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (164, N'MMTCB', N'B', N'Chuẩn nào sử dụng trong cấp presentation?', N'UTP và STP', N'SMTP và HTTP', N'ASCII và EBCDIC', N'TCP và UDP', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (165, N'MMTCB', N'B', N'Đơn vị truyền dữ liệu giữa các cấp trong mạng theo thứ tự', N'bit,frame,packet,data', N'bit,packet,frame,data', N'data,frame,packet,bit', N'data,bit,packet,frame', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (166, N'MMTCB', N'B', N'Mạng Ethernet do cơ quan nào phát minh', N'ANSI', N'ISO', N'IEEE', N'XEROX', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (167, N'MMTCB', N'B', N'Chiều dài loại cáp nào tối đa 100 m? ', N'10Base2', N'10Base5', N'10BaseT', N'10BaseF', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (168, N'MMTCB', N'B', N'Địa chỉ IP 100.150.200.250 có nghĩa là', N'Địa chỉ network 100, địa chỉ host 150.200.250', N'Địa chỉ network 100.150, địa chỉ host 200.250', N'Địa chỉ network 100.150.200, địa chỉ host 250', N'Tất cả đều sai', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (169, N'MMTCB', N'B', N'Switching hun khác hub thông thường ở chỗ nó làm', N'Giảm collision trên mạng', N'Tăng collision trên mạng', N'Giảm congestion trên mạng', N'Tăng congestion trên mạng', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (170, N'MMTCB', N'B', N'Loại cáp nào chỉ truyền dữ liệu 1 chiều', N'Cáp quang', N'Xoắn đôi', N'Đồng trục', N'Tất cả đều đúng', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (171, N'MMTCB', N'B', N'Thiết bị Modem dùng để', N'Tách và ghép tín hiệu', N'Nén và gải nén tín hiệu', N'Mã hóa và giải mã tín hiệu', N'Điều chế và giải điều chế tín hiệu', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (172, N'MMTCB', N'B', N'Việc cấp phát kênh truyền áp dụng cho loại mạng', N'Peer to peer', N'Point to point', N'Broadcast', N'Multiple Access', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (173, N'MMTCB', N'B', N'Mạng nào dùng phương pháp mã hóa Manchester Encoding', N'Ethernet', N'Token-ring', N'Token-bus', N'Tất cả đều đúng ', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (174, N'MMTCB', N'B', N'Phương pháp tìm đường có tính đến thời gian trễ', N'Tìm đường theo chiều sâu', N'Tìm đường theo chiều rộng', N'Tìm đường theo vector khoảng cách', N'Tìm đường theo trạng thái đường truyền', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (175, N'MMTCB', N'B', N'Chuẩn mạng nào khi có dữ liệu không truyền ngay mà chờ 1 thời gian ngẫu nhiên?', N'1-presistent CSMA', N'p-presistent CSMA', N'Non-presistent CSMA', N'CSMA/CD', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (176, N'MMTCB', N'B', N'Phương pháp chèn bit (bit stuffing) được dùng để', N'Phân biệt đầu và cuối frame', N'Bổ sung cho đủ kích thước frame tối thiểu', N'Phân cách nhiều bit 0 bằng bit 1', N'Biến đổi dạng dữ liệu 8 bit ra 16 bit', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (177, N'MMTCB', N'B', N'Để chống nhiễu trên đường truyền tốt nhất, nên dùng loại cáp:', N'Xoắn đôi', N'Đồng trục', N'Cáp quang', N'Mạng không dây', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (178, N'MMTCB', N'B', N'Phần mềm gửi/nhận thư điện tử thuộc cấp nào trong mô hình OSI', N'Data link', N'Network', N'Application', N'Presentation', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (179, N'MMTCB', N'B', N'Chức năng của thiết bị Hub trong mạng LAN', N'Mã hóa tín hiệu', N'Triệt tiêu tín hiệu', N'Phân chia tín hiệu', N'Điều chế tín hiếu', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (180, N'MMTCB', N'B', N'Switch là thiết bị mạng làm việc tương tự như', N'Hub', N'Repeater', N'Router', N'Bridge', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (181, N'MMTCB', N'C', N'Thiết bị nào làm việc trong cấp Network', N'Bridge', N'Repeater', N'Router', N'Gateway', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (182, N'MMTCB', N'C', N'Thiết bị nào cần có bộ nhớ làm buffer', N'Hub', N'Switch', N'Repeater', N'Router', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (183, N'MMTCB', N'C', N'Luật 5-4-3 cho phép tối đa', N'5 segment trong 1 mạng', N'5 repeater trong 1 mạng', N'5 máy tính trong 1 mạng', N'5 máy tính trong 1 segment', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (184, N'MMTCB', N'C', N'Thiết bị nào có thể thêm vào mạng LAN mà không sợ vi phạm luật 5-4-3', N'Router', N'Repeater', N'Máy tính', N'Tất cả đều đúng', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (185, N'MMTCB', N'C', N'Thêm thiết bị nào vào mạng có thể qui phạm luật 5-4-3', N'Router', N'Repeater', N'Bridge', N'Tất cả đều đúng', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (186, N'MMTCB', N'C', N'Mạng nào cóxảy ra xung đột (collision) trên đường truyền', N'Ethernet', N'Token-ring', N'Token-bus', N'Tất cả đều sai', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (187, N'MMTCB', N'C', N'Từ "Broad" trong tên cáp 10Broad36 viết tắt bởi', N'Broadcast', N'Broadbase', N'Broadband', N'Broadway', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (188, N'MMTCB', N'C', N'Protocol nào sử dụng trong cấp Network', N'IP', N'TCP', N'UDP', N'FTP', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (189, N'MMTCB', N'C', N'Protocol nào torng cấp Transport cung cấp dịch vụ không kết nối', N'IP', N'TCP', N'UDP', N'FTP', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (190, N'MMTCB', N'C', N'Protocol nào trong cấp Transport dùng kiểu dịch vụ có kết nối?', N'IP', N'TCP', N'UDP', N'FTP', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (191, N'MMTCB', N'C', N'Địa chỉ IP được chia làm mấy lớp', N'2', N'3', N'4', N'5', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (192, N'MMTCB', N'C', N'Chức năng nào không phải của cấp Network', N'Tìm đường', N'Địa chỉ logic', N'Kiểm soát tắc nghẽn', N'Chất lượng dịch vụ', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (193, N'MMTCB', N'C', N'Phương pháp chèn kí tự dùng để', N'Phân cách các frame', N'Phân biệt dữ liệu và ký tự điều khiển', N'Nhận diện đầu cuối frame', N'Bổ sung cho đủ kich thước frame tối thiểu', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (194, N'MMTCB', N'C', N'Kỹ thuật truyền nào mã hóa trực tiếp dữ liệu ra đường truyền không cần sóng mang', N'Broadcast', N'Digital', N'Baseband', N'Broadband', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (195, N'MMTCB', N'C', N'Sóng viba sử dụng băng tần', N'SHF', N'LF và MF', N'UHF và VHF', N'Tất cả đều đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (196, N'MMTCB', N'C', N'Sóng viba bị ảnh hưởng bời', N'Trời mưa', N'Sấm chớp', N'Giông bão', N'Ánh sáng mặt trời', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (197, N'MMTCB', N'C', N'Đường dây trung kế trong mạng điện thoại sử dụng', N'Tín hiệu số', N'Kỹ thuật dồn kênh', N'Cáp quang, cáp đồng và viba', N'Tất cả đêu đúng', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (198, N'MMTCB', N'C', N'Cáp quang dùng công nghệ dồn kênh nào', N'TDM', N'FDM', N'WDM', N'CDMA', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (199, N'MMTCB', N'C', N'Nhược điểm của phương pháp chèn ký tự', N'Giảm tốc độ đường truyền', N'Tăng phí tổn đường truyền', N'Mất đồng bộ frame', N'Không nhận diện được frame', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (200, N'MMTCB', N'C', N'Mất đồng bộ frame xảy ra đối với phương pháp', N'Chèn bit', N'Đếm ký tự', N'Chèn ký tự', N'Tất cả đều đúng', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (201, N'MMTCB', N'C', N'Mạng nào dùng công nghệ Token-bus', N'FDDI', N'CDDI', N'Fast Ethernet', N'100VG-AnyLAN', N'D', N'TH123   ')

INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (202, N'MMTCB', N'C', N'Thiết bị nào tự trao đổi thông tin lẫn nhau để quản lý mạng', N'Hub', N'Bridge', N'Router', N'Repeater', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (203, N'MMTCB', N'C', N'Tần số sóng điện từ dùng trong mạng vô tuyến sắp theo thứ tự tăng dần', N'Radio,viba,hồng ngoại', N'Radio,hồng ngoại,viba', N'Hồng ngoại,viba,radio', N'Viba,radio,hồng ngoại', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (204, N'MMTCB', N'C', N'Đường dây hạ kế (local loop) trong mạch điện thoại dùng tín hiệu', N'Digital', N'Analog', N'Manchester', N'T1 hoặc E1', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (205, N'MMTCB', N'C', N'Để tránh nhận trùng dữ liệu người ta dùng phương pháp', N'Đánh số thứ tự các frame', N'Quy định kích thước frame cố định', N'Chờ nhận ACK mới gửi frame kế tiếp', N'So sánh và loại bỏ các frame giống nhau', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (206, N'MMTCB', N'C', N'Cơ chế Timer dùng để', N'Đo thời gian chơ frame', N'Tránh tình trạng mất frame', N'Chọn thời điểm truyền frame', N'Kiểm soát thòi gian truyền frame', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (207, N'MMTCB', N'C', N'Cấp nào trong mô hình OSI quan tâm tới topology mạng', N'Transport', N'Network', N'Data link', N'Physical', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (208, N'MMTCB', N'C', N'Loại mạng nào sử dụng trên WAN', N'Ethernet và Token-bus', N'ISDN và Frame relay', N'Token-ring và FDDI', N'SDLC và HDLC', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (209, N'MMTCB', N'C', N'Repeater nhiều port là tên gọi của', N'Hub', N'Host', N'Bridge', N'Router', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (210, N'MMTCB', N'C', N'Đơn vị đo tốc độ đường truyền', N'bps(bit per second)', N'Bps(Byte per second)', N'mps(meter per second)', N'hertz (ccle per second)', N'A', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (211, N'MMTCB', N'C', N'Repeater dùng để', N'Lọc bớt dữ liệu trên mạng', N'Tăng tốc độ lưu thông trên mạng', N'Tăng thời gian trễ trên mạng', N'Mở rộng chiều dài đường truyền', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (212, N'MMTCB', N'C', N'Cáp đồng trục (coaxial)', N'Có 4 đôi dây', N'Không cần repeater', N'Truyền tín hiệu ánh sáng', N'Chống nhiễu tốt hơn UTP', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (213, N'MMTCB', N'C', N'Câp Data link ', N'Truyền dữ liệu cho các cấp khác trong mạng', N'Cung cấp dịch vụ cho chương trình ứng dụng', N'Nhận tín hiệu yếu,lọc,khuếch đại và phát lại trên mạng', N'Bảo đảm đường truyền dữ liệu tin cậy giữa 2 đầu đường truyền', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (214, N'MMTCB', N'C', N'Địa chỉ IP còn gọi là', N'Địa chĩ vật lý', N'Địa chỉ luận lý', N'Địa chỉ thập phân', N'Địa chỉ thập lục phân', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (215, N'MMTCB', N'C', N'Cấp Presentation', N'Thiết lập, quản lý và kết thúc các ứng dụng', N'Hướng dẫn cách mô tả hình ảnh, âm thanh, tiếng nói', N'Cung cấp dịch vụ truyền dữ liệu từ nguồn đến đích', N'Hỗ trợ việc truyền thông trong các ứng dụng như web, mail...', N'C', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (216, N'MMTCB', N'C', N'Tập các luật để định dạng và truyền dữ liệu gọi là', N'Qui luật (rule)', N'Nghi thức (protocol)', N'Tiêu chuẩn (standard)', N'Mô hình (model)', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (217, N'MMTCB', N'C', N'Tại sao cần có tiêu chuẩn về mang', N'Định hướng phát triển phần cứng và phần mềm mới', N'LAN,MAN và WAN sử dụng các thiết bị khác nhau', N'Kết nối mạng giữa các quôc gia khác nhau', N'Tương thích về công nghệ để truyền thông được lẫn nhau', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (218, N'MMTCB', N'C', N'Dữ liệu truyền trên mạng bằng', N'Mã ASCII', N'Số nhị phân', N'Không và một', N'Xung điện áp', N'D', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (219, N'MMTCB', N'C', N'Mạng CSMA/CD', N'Kiểm tra để bảo đảm dữ liệu truyền đến đích', N'Kiểm tra đường truyền nếu rảnh mới truyền dữ liệu', N'Chờ 1 thời gian ngẫu nhiên rồi truyền  dữ liệu kế tiếp', N'Tất cả đều đúng', N'B', N'TH123   ')
INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES (220, N'MMTCB', N'C', N'Địa chỉ MAC ', N'Gồm có 32 bit', N'Còn gọi là địa chỉ logic', N'Nằm trong cấp Network', N'Dùng để phân biệt các máy trong mạng', N'D', N'TH123   ')


INSERT [dbo].[BODE] ([CAUHOI], [MAMH], [TRINHDO], [NOIDUNG], [A], [B], [C], [D], [DAP_AN], [MAGV]) VALUES
    (221, N'CTDL ', N'A', N'Queue hoạt động theo nguyên tắc nào?', N'FIFO', N'LIFO', N'Random', N'Binary', N'A', N'TH101   '),
    (222, N'CTDL ', N'A', N'Stack hoạt động theo nguyên tắc nào?', N'FIFO', N'LIFO', N'Round Robin', N'Hashing', N'B', N'TH101   '),
    (223, N'CTDL ', N'A', N'Cấu trúc dữ liệu nào truy cập phần tử đầu nhanh nhất?', N'Linked list', N'Stack', N'Queue', N'Array', N'D', N'TH101   '),
    (224, N'CTDL ', N'A', N'Thao tác push áp dụng với cấu trúc nào?', N'Tree', N'Stack', N'Graph', N'Heap', N'B', N'TH101   '),
    (225, N'CTDL ', N'A', N'Thao tác enqueue áp dụng với cấu trúc nào?', N'Queue', N'Stack', N'Tree', N'Set', N'A', N'TH101   '),
    (226, N'CTDL ', N'A', N'Tìm kiếm tuyến tính có độ phức tạp xấu nhất là?', N'O(1)', N'O(log n)', N'O(n)', N'O(n log n)', N'C', N'TH101   '),
    (227, N'CTDL ', N'A', N'Mảng bắt đầu đánh chỉ số thường từ?', N'0', N'1', N'2', N'-1', N'A', N'TH101   '),
    (228, N'CTDL ', N'A', N'Linked list đơn mỗi node thường chứa?', N'Data và 1 next', N'2 next', N'Chỉ data', N'Key và color', N'A', N'TH101   '),
    (229, N'CTDL ', N'A', N'Cây nhị phân mỗi node có tối đa bao nhiêu con?', N'1', N'2', N'3', N'4', N'B', N'TH101   '),
    (230, N'CTDL ', N'A', N'Thao tác pop lấy dữ liệu ở đâu trong stack?', N'Đầu', N'Giữa', N'Cuối', N'Đỉnh', N'D', N'TH101   '),
    (231, N'CTDL ', N'A', N'Thao tác dequeue lấy phần tử nào?', N'Cuối hàng đợi', N'Đầu hàng đợi', N'Giữa hàng đợi', N'Bất kỳ', N'B', N'TH101   '),
    (232, N'CTDL ', N'A', N'Bubble sort thuộc nhóm sắp xếp nào?', N'Đổi chỗ', N'Chèn', N'Chọn', N'Trộn', N'A', N'TH101   '),
    (233, N'CTDL ', N'B', N'Binary search yêu cầu điều kiện gì?', N'Dữ liệu đã sắp xếp', N'Dữ liệu ngẫu nhiên', N'Chỉ dành cho queue', N'Chỉ dùng cho tree', N'A', N'TH101   '),
    (234, N'CTDL ', N'B', N'Quick sort có độ phức tạp trung bình là?', N'O(n^2)', N'O(n log n)', N'O(log n)', N'O(n)', N'B', N'TH101   '),
    (235, N'CTDL ', N'B', N'Merge sort dùng ý tưởng nào?', N'Đệ quy chia để trị', N'Tham lam', N'Quy hoạch động', N'Băm', N'A', N'TH101   '),
    (236, N'CTDL ', N'B', N'Node là gì trong linked list?', N'Một phép toán', N'Một phần tử có liên kết', N'Một kiểu sắp xếp', N'Một hash key', N'B', N'TH101   '),
    (237, N'CTDL ', N'B', N'Cây nhị phân tìm kiếm có tính chất nào?', N'Trái > gốc > phải', N'Trái < gốc < phải', N'Không có thứ tự', N'Mỗi node có 3 con', N'B', N'TH101   '),
    (238, N'CTDL ', N'B', N'Duyệt LNR trên BST cho kết quả?', N'Thứ tự giảm dần', N'Thứ tự tăng dần', N'Ngẫu nhiên', N'Chỉ lá là', N'B', N'TH101   '),
    (239, N'CTDL ', N'B', N'Thao tác peek của stack dùng để?', N'Xóa đỉnh', N'Xem đỉnh không xóa', N'Chèn vào đỉnh', N'Đảo ngược stack', N'B', N'TH101   '),
    (240, N'CTDL ', N'B', N'Cấu trúc nào phù hợp nhất để mô phỏng undo?', N'Queue', N'Stack', N'Heap', N'Graph', N'B', N'TH101   '),
    (241, N'CTDL ', N'B', N'Heap nhị phân là?', N'Cây cân bằng tuyệt đối', N'Cây gần đầy và có tính chất heap', N'Danh sách liên kết', N'Bảng băm', N'B', N'TH101   '),
    (242, N'CTDL ', N'B', N'Insertion sort tốt cho trường hợp nào?', N'Dữ liệu gần đã sắp xếp', N'Dữ liệu rất lớn', N'Dữ liệu phân tán', N'Chỉ dùng cho queue', N'A', N'TH101   '),
    (243, N'CTDL ', N'B', N'Selection sort mỗi vòng lặp sẽ?', N'Chọn phần tử nhỏ nhất đưa về đúng vị trí', N'Chia mảng làm đôi', N'Trộn hai dãy', N'Đổi chỗ ngẫu nhiên', N'A', N'TH101   '),
    (244, N'CTDL ', N'B', N'Độ phức tạp truy cập node thứ k của linked list đơn là?', N'O(1)', N'O(log n)', N'O(k)', N'O(n log n)', N'C', N'TH101   '),
    (245, N'CTDL ', N'C', N'AVL tree được dùng để?', N'Cân bằng cây tìm kiếm', N'Lưu queue', N'Tạo hash', N'Duyệt đồ thị', N'A', N'TH101   '),
    (246, N'CTDL ', N'C', N'Rotation trong AVL tree nhằm mục đích gì?', N'Tăng bộ nhớ', N'Giữ cây cân bằng', N'Xóa node lá', N'Tìm đường đi ngắn nhất', N'B', N'TH101   '),
    (247, N'CTDL ', N'C', N'Hash collision xảy ra khi?', N'Hai khóa trùng vị trí băm', N'Khóa âm', N'Mảng rỗng', N'Dữ liệu đã sắp xếp', N'A', N'TH101   '),
    (248, N'CTDL ', N'C', N'Chaining là kỹ thuật giải quyết?', N'Đệ quy', N'Collision trong hash', N'Sắp xếp trộn', N'Cân bằng cây', N'B', N'TH101   '),
    (249, N'CTDL ', N'C', N'BFS thường sử dụng cấu trúc nào?', N'Stack', N'Queue', N'Heap', N'Array 2 chiều', N'B', N'TH101   '),
    (250, N'CTDL ', N'C', N'DFS thường sử dụng cấu trúc nào?', N'Queue', N'Stack', N'Set', N'Hash map', N'B', N'TH101   '),
    (251, N'CTDL ', N'C', N'Dijkstra dùng để tìm?', N'Cây khung nhỏ nhất', N'Đường đi ngắn nhất', N'Chu trình Euler', N'Liên thông mạnh', N'B', N'TH101   '),
    (252, N'CTDL ', N'C', N'Min-heap có tính chất nào ở nút gốc?', N'Lớn nhất', N'Nhỏ nhất', N'Ngẫu nhiên', N'Luôn bằng 0', N'B', N'TH101   '),
    (253, N'CTDL ', N'C', N'Red-black tree đảm bảo điều gì?', N'Cân bằng tương đối', N'Cân bằng tuyệt đối', N'Không cân bằng', N'Chỉ có node đỏ', N'A', N'TH101   '),
    (254, N'CTDL ', N'C', N'Prim và Kruskal dùng cho bài toán nào?', N'Đường đi ngắn nhất', N'Cây khung nhỏ nhất', N'Tìm chuỗi', N'Bảng băm', N'B', N'TH101   '),
    (255, N'CTDL ', N'C', N'Độ phức tạp trung bình của thao tác tìm kiếm trong hash table tốt là?', N'O(1)', N'O(n)', N'O(log n)', N'O(n log n)', N'A', N'TH101   '),
    (256, N'CTDL ', N'C', N'Topo sort áp dụng cho?', N'Graph vô hướng', N'DAG', N'Cây nhị phân', N'Queue', N'B', N'TH101   '),
    (257, N'CSDL ', N'A', N'Hệ quản trị cơ sở dữ liệu viết tắt là?', N'DBMS', N'DNS', N'HTML', N'LAN', N'A', N'TH657   '),
    (258, N'CSDL ', N'A', N'Lệnh dùng để truy vấn dữ liệu là?', N'UPDATE', N'DELETE', N'SELECT', N'DROP', N'C', N'TH657   '),
    (259, N'CSDL ', N'A', N'Khóa chính dùng để?', N'Đánh dấu bản ghi duy nhất', N'Lưu file', N'Tăng tốc RAM', N'Nối mạng', N'A', N'TH657   '),
    (260, N'CSDL ', N'A', N'Khóa ngoại dùng để?', N'Ràng buộc liên kết giữa bảng', N'Tạo view', N'Để mã hóa', N'Tính tổng', N'A', N'TH657   '),
    (261, N'CSDL ', N'A', N'Lệnh thêm dữ liệu là?', N'ALTER', N'INSERT', N'JOIN', N'GROUP', N'B', N'TH657   '),
    (262, N'CSDL ', N'A', N'Lệnh sửa dữ liệu là?', N'UPDATE', N'ORDER BY', N'CREATE', N'HAVING', N'A', N'TH657   '),
    (263, N'CSDL ', N'A', N'Lệnh xóa dữ liệu là?', N'DELETE', N'SELECT', N'VALUES', N'FROM', N'A', N'TH657   '),
    (264, N'CSDL ', N'A', N'Mệnh đề lọc dữ liệu là?', N'GROUP BY', N'ORDER BY', N'WHERE', N'INNER', N'C', N'TH657   '),
    (265, N'CSDL ', N'A', N'Kiểu join lấy dữ liệu khớp ở cả hai bảng là?', N'LEFT JOIN', N'INNER JOIN', N'RIGHT JOIN', N'FULL JOIN', N'B', N'TH657   '),
    (266, N'CSDL ', N'A', N'NULL trong CSDL thể hiện?', N'Giá trị bằng 0', N'Không có giá trị', N'Chuỗi rỗng', N'Sai ràng buộc', N'B', N'TH657   '),
    (267, N'CSDL ', N'A', N'Bảng dữ liệu gồm?', N'Hàng và cột', N'Chỉ cột', N'Chỉ hàng', N'Chỉ khóa', N'A', N'TH657   '),
    (268, N'CSDL ', N'A', N'Constraint UNIQUE dùng để?', N'Cấm giá trị trùng', N'Cho phép NULL', N'Nối bảng', N'Tính tổng cột', N'A', N'TH657   '),
    (269, N'CSDL ', N'B', N'Chuẩn hóa 1NF yêu cầu?', N'Thuộc tính nguyên tố', N'Không có khóa', N'Không cần PK', N'Chỉ 1 bảng', N'A', N'TH657   '),
    (270, N'CSDL ', N'B', N'Chuẩn hóa 2NF loại bỏ?', N'Phụ thuộc bộ phận', N'Phụ thuộc bắc cầu', N'Dữ liệu trùng khóa ngoại', N'Giá trị NULL', N'A', N'TH657   '),
    (271, N'CSDL ', N'B', N'Chuẩn hóa 3NF loại bỏ?', N'Phụ thuộc bộ phận', N'Phụ thuộc bắc cầu', N'Chỉ mục', N'Ràng buộc check', N'B', N'TH657   '),
    (272, N'CSDL ', N'B', N'GROUP BY dùng để?', N'Sắp xếp', N'Nhóm dữ liệu', N'Nối bảng', N'Tạo bảng', N'B', N'TH657   '),
    (273, N'CSDL ', N'B', N'HAVING dùng để?', N'Lọc sau khi nhóm', N'Lọc trước khi nhóm', N'Tạo khóa', N'Xóa bảng', N'A', N'TH657   '),
    (274, N'CSDL ', N'B', N'Chỉ mục index giúp?', N'Tăng tốc truy vấn', N'Tăng dung lượng RAM', N'Giảm cột', N'Bỏ khóa chính', N'A', N'TH657   '),
    (275, N'CSDL ', N'B', N'Transaction có tính chất ACID, chữ C là?', N'Consistent', N'Concurrent', N'Connected', N'Computed', N'A', N'TH657   '),
    (276, N'CSDL ', N'B', N'COMMIT dùng để?', N'Hủy giao dịch', N'Lưu giao dịch', N'Khóa bảng', N'Khởi tạo DB', N'B', N'TH657   '),
    (277, N'CSDL ', N'B', N'ROLLBACK dùng để?', N'Lưu thay đổi', N'Phục hồi giao dịch', N'Tạo view', N'Xem log', N'B', N'TH657   '),
    (278, N'CSDL ', N'B', N'View là?', N'Bảng ảo từ câu truy vấn', N'Bảng vật lý', N'Khóa chính', N'Stored procedure', N'A', N'TH657   '),
    (279, N'CSDL ', N'B', N'Lệnh tạo bảng là?', N'CREATE TABLE', N'ALTER TABLE', N'DROP TABLE', N'SELECT INTO', N'A', N'TH657   '),
    (280, N'CSDL ', N'B', N'Lệnh sửa cấu trúc bảng là?', N'INSERT', N'UPDATE', N'ALTER TABLE', N'TRUNCATE', N'C', N'TH657   '),
    (281, N'CSDL ', N'C', N'Isolation level dùng để kiểm soát?', N'Trạng thái server', N'Mức độ cô lập giao dịch', N'Kích thước bảng', N'Khóa chính', N'B', N'TH657   '),
    (282, N'CSDL ', N'C', N'Deadlock xảy ra khi?', N'Hai giao dịch chờ nhau giải phóng tài nguyên', N'Không có index', N'Bảng rỗng', N'Server tắt', N'A', N'TH657   '),
    (283, N'CSDL ', N'C', N'Stored procedure là?', N'Tập lệnh SQL được lưu để tái sử dụng', N'Bảng tạm', N'Khóa ngoại', N'Một index', N'A', N'TH657   '),
    (284, N'CSDL ', N'C', N'Trigger thường kích hoạt khi?', N'Có sự kiện INSERT UPDATE DELETE', N'Khởi động máy', N'Tạo user', N'Mở file', N'A', N'TH657   '),
    (285, N'CSDL ', N'C', N'Clustered index sắp xếp?', N'Dữ liệu vật lý trong bảng', N'Chỉ bộ nhớ', N'Chỉ log', N'Chỉ khóa ngoại', N'A', N'TH657   '),
    (286, N'CSDL ', N'C', N'Nonclustered index lưu?', N'Cấu trúc chỉ mục tách dữ liệu', N'Dữ liệu trùng lặp toàn bộ', N'Chỉ giao dịch', N'Chỉ khóa chính', N'A', N'TH657   '),
    (287, N'CSDL ', N'C', N'Backup full dùng để?', N'Sao lưu toàn bộ CSDL', N'Xóa dữ liệu cũ', N'Nối bảng', N'Tạo view', N'A', N'TH657   '),
    (288, N'CSDL ', N'C', N'Restore log dùng khi?', N'Phục hồi theo chuỗi backup', N'Tạo index', N'Chuẩn hóa 3NF', N'Tìm khóa chính', N'A', N'TH657   '),
    (289, N'CSDL ', N'C', N'Phân mảnh dữ liệu thường dùng để?', N'Tăng khả năng mở rộng', N'Giảm bảo mật', N'Bỏ transaction', N'Bỏ index', N'A', N'TH657   '),
    (290, N'CSDL ', N'C', N'Replication dùng để?', N'Đồng bộ dữ liệu giữa các hệ thống', N'Xóa dữ liệu trùng', N'Thay khóa chính', N'Đổi kiểu dữ liệu', N'A', N'TH657   '),
    (291, N'CSDL ', N'C', N'Dirty read là?', N'Đọc dữ liệu chưa commit', N'Đọc dữ liệu đã xóa', N'Đọc dữ liệu đã mã hóa', N'Đọc dữ liệu trùng', N'A', N'TH657   '),
    (292, N'CSDL ', N'C', N'Phân quyền SELECT cho user dùng lệnh nào?', N'REVOKE', N'GRANT', N'DENY', N'BACKUP', N'B', N'TH657   ');

SET IDENTITY_INSERT [dbo].[BODE] OFF

INSERT INTO GIAOVIEN_DANGKY (MAGV, MALOP, MAMH, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN) VALUES
    (N'TH123   ', N'TH04           ', N'MMTCB', N'A', '2026-06-10 08:00:00', 1, 20, 45),
    (N'TH123   ', N'TH04           ', N'MMTCB', N'B', '2026-06-17 08:00:00', 2, 20, 45),
    (N'TH234   ', N'TH05           ', N'AVCB ', N'A', '2026-06-11 09:00:00', 1, 15, 30),
    (N'TH234   ', N'TH05           ', N'AVCB ', N'C', '2026-06-18 09:00:00', 2, 12, 25),
    (N'TH101   ', N'TH06           ', N'CTDL ', N'A', '2026-06-12 10:00:00', 1, 12, 25),
    (N'TH101   ', N'TH06           ', N'CTDL ', N'B', '2026-06-19 10:00:00', 2, 12, 25),
    (N'TH657   ', N'TH07           ', N'CSDL ', N'B', '2026-06-13 13:30:00', 1, 12, 25),
    (N'TH657   ', N'TH07           ', N'CSDL ', N'C', '2026-06-20 13:30:00', 2, 10, 20),
    (N'TH657   ', N'TH08           ', N'MMTCB', N'C', '2026-06-14 14:00:00', 1, 15, 30),
    (N'TH657   ', N'VT04           ', N'CSDL ', N'A', '2026-06-15 08:30:00', 1, 10, 20),
    (N'TH234   ', N'D18CQCN01      ', N'AVCB ', N'B', '2026-06-16 15:00:00', 1, 12, 25),
    (N'TH657   ', N'D18CQCN01      ', N'CSDL ', N'A', '2026-06-21 15:00:00', 2, 10, 20);

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH04           ', N'MMTCB', 1, CAUHOI
FROM BODE
WHERE CAUHOI = 1 OR CAUHOI BETWEEN 3 AND 15 OR CAUHOI BETWEEN 161 AND 166;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH04           ', N'MMTCB', 2, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 142 AND 155 OR CAUHOI BETWEEN 181 AND 186;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH05           ', N'AVCB ', 1, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 20 AND 29 OR CAUHOI = 50 OR CAUHOI BETWEEN 30 AND 33;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH05           ', N'AVCB ', 2, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 40 AND 49 OR CAUHOI BETWEEN 70 AND 71;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH06           ', N'CTDL ', 1, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 221 AND 229 OR CAUHOI BETWEEN 233 AND 235;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH06           ', N'CTDL ', 2, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 233 AND 241 OR CAUHOI BETWEEN 245 AND 247;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH07           ', N'CSDL ', 1, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 269 AND 277 OR CAUHOI BETWEEN 281 AND 283;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH07           ', N'CSDL ', 2, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 281 AND 290;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'TH08           ', N'MMTCB', 1, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 181 AND 195;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'VT04           ', N'CSDL ', 1, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 257 AND 264 OR CAUHOI BETWEEN 269 AND 270;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'D18CQCN01      ', N'AVCB ', 1, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 30 AND 38 OR CAUHOI BETWEEN 40 AND 42;

INSERT INTO CT_GIAOVIEN_DANGKY (MALOP, MAMH, LAN, CAUHOI)
SELECT N'D18CQCN01      ', N'CSDL ', 2, CAUHOI
FROM BODE
WHERE CAUHOI BETWEEN 257 AND 263 OR CAUHOI BETWEEN 269 AND 271;

/* =========================================
                TABLE BAITHI
========================================= */

CREATE TABLE BAITHI (
    ID INT IDENTITY(1,1) PRIMARY KEY,

    MASV NCHAR(8),

    MALOP NCHAR(15) NOT NULL,

    MAMH NCHAR(5) NOT NULL,

    LAN SMALLINT
        CHECK (LAN BETWEEN 1 AND 2),

    NGAYTHI DATETIME DEFAULT GETDATE(),

    DIEM FLOAT
        CHECK (DIEM BETWEEN 0 AND 10),

    THOIGIANBATDAU DATETIME,
    THOIGIANKETTHUC DATETIME
)

/* =========================================
            TABLE CHITIETBAITHI
========================================= */

CREATE TABLE CHITIETBAITHI (
    BAITHI_ID INT NOT NULL,

    CAUHOI INT NOT NULL,

    THUTU INT NOT NULL,

    DAPAN_SV NCHAR(1)
        CHECK (DAPAN_SV IN ('A', 'B', 'C', 'D')),

    CONSTRAINT PK_CTBT
        PRIMARY KEY (BAITHI_ID, CAUHOI)
)

SET IDENTITY_INSERT [dbo].[BAITHI] ON

INSERT INTO BAITHI (ID, MASV, MALOP, MAMH, LAN, NGAYTHI, DIEM, THOIGIANBATDAU, THOIGIANKETTHUC) VALUES
    (1, N'001     ', N'TH04           ', N'MMTCB', 1, '2026-06-10 08:00:00', 10.0, '2026-06-10 08:00:00', '2026-06-10 08:35:00'),
    (2, N'002     ', N'TH04           ', N'MMTCB', 1, '2026-06-10 08:05:00', 8.0, '2026-06-10 08:05:00', '2026-06-10 08:44:00'),
    (3, N'003     ', N'TH04           ', N'MMTCB', 2, '2026-06-17 08:00:00', 7.5, '2026-06-17 08:00:00', '2026-06-17 08:40:00'),
    (4, N'005     ', N'TH05           ', N'AVCB ', 1, '2026-06-11 09:00:00', 4.0, '2026-06-11 09:00:00', '2026-06-11 09:24:00'),
    (5, N'006     ', N'TH05           ', N'AVCB ', 2, '2026-06-18 09:00:00', 6.7, '2026-06-18 09:00:00', '2026-06-18 09:21:00'),
    (6, N'008     ', N'TH06           ', N'CTDL ', 1, '2026-06-12 10:00:00', 5.0, '2026-06-12 10:00:00', '2026-06-12 10:20:00'),
    (7, N'012     ', N'TH06           ', N'CTDL ', 2, '2026-06-19 10:00:00', 9.2, '2026-06-19 10:00:00', '2026-06-19 10:18:00'),
    (8, N'017     ', N'TH07           ', N'CSDL ', 1, '2026-06-13 13:30:00', 7.5, '2026-06-13 13:30:00', '2026-06-13 13:52:00'),
    (9, N'027     ', N'VT04           ', N'CSDL ', 1, '2026-06-15 08:30:00', 8.0, '2026-06-15 08:30:00', '2026-06-15 08:47:00'),
    (10, N'032     ', N'D18CQCN01      ', N'AVCB ', 1, '2026-06-16 15:00:00', 5.8, '2026-06-16 15:00:00', '2026-06-16 15:23:00');

SET IDENTITY_INSERT [dbo].[BAITHI] OFF;

;WITH ExamSeed AS (
    SELECT *
    FROM (VALUES
        (1, N'TH04           ', N'MMTCB', 1, 20, 0),
        (2, N'TH04           ', N'MMTCB', 1, 16, 2),
        (3, N'TH04           ', N'MMTCB', 2, 15, 3),
        (4, N'TH05           ', N'AVCB ', 1, 6, 7),
        (5, N'TH05           ', N'AVCB ', 2, 8, 3),
        (6, N'TH06           ', N'CTDL ', 1, 6, 4),
        (7, N'TH06           ', N'CTDL ', 2, 11, 1),
        (8, N'TH07           ', N'CSDL ', 1, 9, 2),
        (9, N'VT04           ', N'CSDL ', 1, 8, 1),
        (10, N'D18CQCN01      ', N'AVCB ', 1, 7, 4)
    ) AS S(EXAM_ID, MALOP, MAMH, LAN, CORRECT_COUNT, WRONG_COUNT)
),
ExamQuestions AS (
    SELECT
        S.EXAM_ID,
        C.CAUHOI,
        ROW_NUMBER() OVER (PARTITION BY S.EXAM_ID ORDER BY C.CAUHOI) AS THUTU,
        S.CORRECT_COUNT,
        S.WRONG_COUNT
    FROM ExamSeed S
    INNER JOIN CT_GIAOVIEN_DANGKY C
        ON C.MALOP = S.MALOP
       AND C.MAMH = S.MAMH
       AND C.LAN = S.LAN
)
INSERT INTO CHITIETBAITHI (BAITHI_ID, CAUHOI, THUTU, DAPAN_SV)
SELECT
    EQ.EXAM_ID,
    EQ.CAUHOI,
    EQ.THUTU,
    CASE
        WHEN EQ.THUTU <= EQ.CORRECT_COUNT THEN B.DAP_AN
        WHEN EQ.THUTU <= EQ.CORRECT_COUNT + EQ.WRONG_COUNT THEN
            CASE B.DAP_AN
                WHEN 'A' THEN 'B'
                WHEN 'B' THEN 'C'
                WHEN 'C' THEN 'D'
                ELSE 'A'
            END
        ELSE NULL
    END
FROM ExamQuestions EQ
INNER JOIN BODE B
    ON B.CAUHOI = EQ.CAUHOI;

/* =========================================
            ADD CONSTRAINTS
========================================= */

ALTER TABLE SINHVIEN
ADD CONSTRAINT FK_SINHVIEN_LOP
FOREIGN KEY (MALOP)
REFERENCES LOP(MALOP)

ALTER TABLE SINHVIEN
ADD CONSTRAINT FK_SINHVIEN_TAIKHOAN
FOREIGN KEY (MASV)
REFERENCES TAIKHOAN(MA);

ALTER TABLE PASSWORD_RESET_OTP
ADD CONSTRAINT FK_PASSWORD_RESET_OTP_TAIKHOAN
FOREIGN KEY (ACCOUNT_ID)
REFERENCES TAIKHOAN(MA);

ALTER TABLE GIAOVIEN_DANGKY
ADD CONSTRAINT FK_GVDK_GV
FOREIGN KEY (MAGV)
REFERENCES GIAOVIEN(MAGV)

ALTER TABLE GIAOVIEN_DANGKY
ADD CONSTRAINT FK_GVDK_LOP
FOREIGN KEY (MALOP)
REFERENCES LOP(MALOP)

ALTER TABLE GIAOVIEN_DANGKY
ADD CONSTRAINT FK_GVDK_MH
FOREIGN KEY (MAMH)
REFERENCES MONHOC(MAMH)

ALTER TABLE BODE
ADD CONSTRAINT FK_BODE_MONHOC
FOREIGN KEY (MAMH)
REFERENCES MONHOC(MAMH)

ALTER TABLE BODE
ADD CONSTRAINT FK_BODE_GIAOVIEN
FOREIGN KEY (MAGV)
REFERENCES GIAOVIEN(MAGV)

ALTER TABLE BAITHI
ADD CONSTRAINT FK_BAITHI_SINHVIEN
FOREIGN KEY (MASV)
REFERENCES SINHVIEN(MASV)

ALTER TABLE BAITHI
ADD CONSTRAINT FK_BAITHI_MONHOC
FOREIGN KEY (MAMH)
REFERENCES MONHOC(MAMH)

ALTER TABLE CHITIETBAITHI
ADD CONSTRAINT FK_CTBT_BAITHI
FOREIGN KEY (BAITHI_ID)
REFERENCES BAITHI(ID)
ON DELETE CASCADE

ALTER TABLE BAITHI
ADD CONSTRAINT FK_BAITHI_GVDK
FOREIGN KEY (MALOP, MAMH, LAN)
REFERENCES GIAOVIEN_DANGKY(MALOP, MAMH, LAN)

ALTER TABLE CHITIETBAITHI
ADD CONSTRAINT FK_CTBT_BODE
FOREIGN KEY (CAUHOI)
REFERENCES BODE(CAUHOI)

/* =========================================
                INDEX
========================================= */

CREATE INDEX IX_BODE_MAMH_TRINHDO
ON BODE(MAMH, TRINHDO)

CREATE INDEX IX_SINHVIEN_MALOP
ON SINHVIEN(MALOP)

CREATE INDEX IX_BAITHI_MASV
ON BAITHI(MASV)

COMMIT TRAN

PRINT N'TẠO DATABASE THÀNH CÔNG'

END TRY

BEGIN CATCH

IF @@TRANCOUNT > 0
    ROLLBACK TRAN

PRINT N'CÓ LỖI -> XÓA DATABASE'

PRINT ERROR_MESSAGE()

USE master

IF DB_ID('THITRACNGHIEM') IS NOT NULL
BEGIN
    DROP DATABASE THITRACNGHIEM
END

END CATCH
GO
