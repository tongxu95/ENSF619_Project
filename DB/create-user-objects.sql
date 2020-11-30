DROP DATABASE IF EXISTS movieuser;
CREATE DATABASE movieuser; 
USE movieuser;


DROP TABLE IF EXISTS REGISTERED;
CREATE TABLE REGISTERED (
  Username				varchar(15) NOT NULL,
  Password				varchar(15) NOT NULL,
  Fee_renew_date		date,
  Name					varchar(50),
  Address		        varchar(50),
  Bank					varchar(15),
  Email					varchar(50),
  Card_no			    varchar(16),
  Card_cvv				int(3),
  Card_exp				int(4),
  PRIMARY key (Username)
);


DROP TABLE IF EXISTS VOUCHER;
CREATE TABLE VOUCHER (
  VoucherID				int(5) NOT NULL,
  Credit				double(5,2),
  Expr_Date				date,
  PRIMARY key (VoucherID)
);


DROP TABLE IF EXISTS BANK;
CREATE TABLE BANK (
  Name         			varchar(15) NOT NULL,
  PRIMARY key (Name)
);


DROP TABLE IF EXISTS CREDITCARD;
CREATE TABLE CREDITCARD (
  BankName			    varchar(15) NOT NULL, 
  Card_no			    varchar(16),
  Card_cvv				int(3),
  Card_exp				int(4),
  PRIMARY key (Card_no),
  FOREIGN key (BankName) REFERENCES BANK(Name)
  		ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO BANK VALUES
	('Scotiabank'),
	('TD Canada Trust'),
    ('CIBC'),
    ('RBC'),
    ('BMO');
    
INSERT INTO CREDITCARD VALUES
	('Scotiabank','4929844610628530',138,0524),
    ('Scotiabank','2720999154954117',290,0323),
    ('TD Canada Trust','4532316556491131',488,0921),
    ('CIBC','5343911733879095',881,0122),
    ('RBC','5409797916016568',723,0722),
    ('BMO','6011014819581872',631,0922);

INSERT INTO VOUCHER VALUES 
	(20000,14.95,'2021-11-28'),
    (20001,12.71,'2021-01-20'),
    (20002,17.95,'2021-05-12'),
    (20003,15.26,'2021-06-26'),
    (20004,14.95,'2020-12-30');

INSERT INTO REGISTERED VALUES
	('alfred96', 'Pa$sword','2021-05-21','Alfred L. McCray','718 Village Green Road, Lake Verde, PE','Scotiabank','alfred1996@gmail.com','4929844610628530',138,0524),
    ('Mike331','M@Rankin','2020-10-21','Mike A. Rankin','3226 Eglinton Avenue, Toronto, ON','Scotiabank','mike331@hotmail.com','2720999154954117',290,0323),
    ('Lorraine20','L*Thomson','2021-10-31','Lorraine C. Thompson','4025 Roger Street, Port Alberni, BC','TD Canada Trust','thomson331@shaw.ca','4532316556491131',488,0921);
