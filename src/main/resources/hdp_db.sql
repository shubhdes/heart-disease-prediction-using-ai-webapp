create database hdp_db;

use hdp_db;  

drop table login;

drop table customers;

drop table prediction;

drop table otp;

create table login(userid varchar(100) primary key, password varchar(20));

create table customers(emailid varchar(100) primary key, fname varchar(50),lname varchar(50), mobile varchar(10), secret_question varchar(500), answer varchar(500));

create table prediction(prediction_id int primary key auto_increment, emailid varchar(100), age varchar(100), gender varchar(100), cig varchar(100), chol varchar(100), dia varchar(100), sys varchar(100), diab varchar(100), glu varchar(100), heartrate varchar(100), result varchar(100));

create table otp(otp_id int primary key auto_increment, emailid varchar(100), random varchar(100), status varchar(100), ts varchar(100));
