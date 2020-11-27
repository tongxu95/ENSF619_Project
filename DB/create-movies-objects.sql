DROP DATABASE IF EXISTS movie;
CREATE DATABASE movie; 
USE movie;


DROP TABLE IF EXISTS REGISTERED;
CREATE TABLE REGISTERED (
  Username				varchar(15) NOT NULL,
  Password				varchar(15) NOT NULL,
  Name					varchar(50),
  Address		        varchar(50),
  Bank					varchar(50),
  Email					varchar(50),
  Card_no			    int(16),
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
  BankID				int(5) NOT NULL,
  Name         			varchar(50),
  PRIMARY key (BankID)
);


DROP TABLE IF EXISTS CREDITCARD;
CREATE TABLE CREDITCARD (
  BankID			    int(5) NOT NULL, 
  Card_no			    int(16),
  Card_cvv				int(3),
  Card_exp				int(4),
  PRIMARY key (ToolID),
  FOREIGN key (BankID) REFERENCES BANK(BankID)
  		ON DELETE CASCADE
        ON UPDATE CASCADE
);

