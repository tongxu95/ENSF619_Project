DROP DATABASE IF EXISTS BOOKINGMODEL;
CREATE DATABASE BOOKINGMODEL; 
USE BOOKINGMODEL;

DROP TABLE IF EXISTS MOVIES;
CREATE TABLE MOVIES (
	Name varchar(25) not null,
    primary key (Name)
);
INSERT INTO MOVIES (Name)
VALUES
("Dodgeball"),
("Zombieland"),
("The Avengers"),
("The Lord of the Rings"),
("21 Jump Street");


DROP TABLE IF EXISTS THEATER;
CREATE TABLE THEATER (
	Name varchar(50) not null,
    primary key (Name)
);
INSERT INTO THEATER (Name) 
VALUES
("GreatMoviesHere"),
("OnlyMediocreMovies"),
("WeOnlyPlayBottomTierComedies");

DROP TABLE IF EXISTS SHOWTIME;
CREATE TABLE SHOWTIME (
	ShowTimeID integer not null,
	MovieName varchar(50) not null,
    TheaterName varchar(50) not null,
    TheaterNumber varchar(4), 
    ShowDate Date,
    ShowTime time,
    price integer,
    primary key (ShowTimeID),
    foreign key (MovieName) references MOVIES(Name),
    foreign key (TheaterName) references THEATER(Name)
);
INSERT INTO SHOWTIME (ShowTimeID, MovieName, TheaterName, TheaterNumber, ShowDate, ShowTime, price)
VALUES
(1, "Dodgeball", "GreatMoviesHere", 1, '2020-12-01', '10:00', 15),
(2, "Zombieland", "GreatMoviesHere", 1, '2020-12-02', '10:00', 15),
(3, "Dodgeball", "GreatMoviesHere", 1, '2020-12-03', '10:00', 15),
(4, "Zombieland", "GreatMoviesHere", 1, '2020-12-04', '10:00', 15),
(5, "The Lord of the Rings", "GreatMoviesHere", 1, '2020-12-05', '10:00', 15),
(6, "The Avengers", "OnlyMediocreMovies", 1, '2020-12-01', '10:00', 10),
(7, "The Avengers", "OnlyMediocreMovies", 1, '2020-12-02', '10:00', 10),
(8, "The Avengers", "OnlyMediocreMovies", 1, '2020-12-03', '10:00', 10),
(9, "The Avengers", "OnlyMediocreMovies", 1, '2020-12-04', '10:00', 10),
(10, "21 Jump Street", "WeOnlyPlayBottomTierComedies", 1, '2020-12-01', '10:00', 2),
(11, "21 Jump Street", "WeOnlyPlayBottomTierComedies", 1, '2020-12-02', '10:00', 2);

DROP TABLE IF EXISTS BOOKING;
CREATE TABLE BOOKING (
	Booking_id integer,
    ShowTimeID integer,
    SeatRow integer,
    SeatColumn integer,
    userType varchar(50) not null,
    foreign key (ShowTimeID) references SHOWTIME(ShowTimeID)
);
INSERT INTO BOOKING (Booking_id, ShowTimeID, SeatRow, SeatColumn, userType)
VALUES
(1001, 1, 4, 5, "Registered"),
(1002, 1, 4, 6, "User"),
(1003, 1, 4, 7, "User"),
(1004, 2, 5, 8, "User"),
(1005, 2, 6, 9, "Registered"),
(1006, 2, 4, 5, "Registered"),
(1007, 3, 4, 6, "User"),
(1008, 3, 4, 7, "User"),
(1009, 3, 5, 8, "User"),
(1010, 4, 5, 5, "Registered"),
(1011, 4, 6, 9, "Registered"),
(1012, 4, 7, 4, "Registered"),
(1013, 5, 7, 4, "Registered"),
(1014, 6, 7, 4, "Registered"),
(1015, 7, 7, 4, "User"),
(1016, 7, 1, 1, "User"),
(1017, 7, 2, 2, "User"),
(1018, 7, 3, 3, "User"),
(1019, 7, 4, 4, "User"),
(1020, 7, 5, 5, "User"),
(1021, 8, 5, 5, "User"),
(1022, 9, 5, 5, "User"),
(1023, 9, 5, 6, "User");
















