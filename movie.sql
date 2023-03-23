CREATE DATABASE IF NOT EXISTS CINEMABOOKINGSYSTEM;
USE CINEMABOOKINGSYSTEM;

CREATE TABLE IF NOT EXISTS USERTYPE( 
userTypeID int,
PRIMARY KEY(userTypeID),
UserTypeName varchar(255)
);

CREATE TABLE IF NOT EXISTS ACTIVE(
activeID int,
PRIMARY KEY(activeID),
ActiveStatus varchar(255)
);

CREATE TABLE IF NOT EXISTS USER( 
userID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY(userID),
password varchar(255) NOT NULL,
firstName varchar(255),
lastName varchar(255),
email varchar(255),
USERTYPE int,
FOREIGN KEY (USERTYPE) REFERENCES USERTYPE (userTypeID),
billingAddress varchar(255),
ACTIVE int,
FOREIGN KEY (ACTIVE) REFERENCES ACTIVE (activeID),
confirm int,
cardnum varchar(255),
securitynum varchar(255),
expmonth int,
expdate int
);

INSERT INTO USERTYPE VALUES(1, "ADMIN");
INSERT INTO USERTYPE VALUES(2, "CUSTOMER");
INSERT INTO USERTYPE VALUES(3, "EMPLOYEE");
INSERT INTO ACTIVE VALUES(0, "INACTIVE");
INSERT INTO ACTIVE VALUES(1, "ACTIVE");

CREATE TABLE IF NOT EXISTS ACCOUNTS(
cardNo int, 
PRIMARY KEY (cardNo), 
userID int,
FOREIGN KEY (userID) REFERENCES USER(userID),
TYPE varchar(255),
expirationDate DATE,
billingAddress varchar(255)
);

CREATE TABLE IF NOT EXISTS USRating (
ratingID int,
PRIMARY KEY (ratingID),
ratingCode varchar(255)
);

INSERT INTO USRating VALUES(1, "PG");
INSERT INTO USRating VALUES(2, "PG-13");
INSERT INTO USRating VALUES(3, "R");




CREATE TABLE IF NOT EXISTS MOVIE(
movieID int NOT NULL,
PRIMARY KEY (movieID),
title varchar(255) NOT NULL,
casting varchar(255),
genre varchar(255) NOT NULL,
producer varchar(255),
duration varchar(255) NOT NULL,
trailerPicture varchar(255) NOT NULL,
trailerVideo varchar(255) NOT NULL,
review int NOT NULL,
ratingID int NOT NULL,
FOREIGN KEY (ratingID) REFERENCES USRating (ratingID)
);

CREATE TABLE IF NOT EXISTS AUDITORIUM (
audID int,
PRIMARY KEY(audID), 
audName varchar(255),
noOfSeats int
);

CREATE TABLE IF NOT EXISTS SEAT (
seatID int,
PRIMARY KEY (seatID),
audID int, 
FOREIGN KEY (audID) REFERENCES AUDITORIUM (audID),
isBooked int
);

CREATE TABLE IF NOT EXISTS SHOWTIMES (
showID int,
PRIMARY KEY (showID),
timeStamp time
);

CREATE TABLE IF NOT EXISTS MOVIESHOW (
showID int,
PRIMARY KEY (showID),
movieID int,
FOREIGN KEY (movieID) REFERENCES MOVIE (movieID),
auditoriumID int,
FOREIGN KEY (auditoriumID) REFERENCES AUDITORIUM (AudID),
availableSeats int,
showStart DATETIME,
timeFilled int
);

CREATE TABLE IF NOT EXISTS TICKETTYPE (
ticketID int,
PRIMARY KEY (ticketID),
ticketType varChar(255),
ticketPrice float
);

INSERT INTO TICKETTYPE VALUES(1, "CHILD", 10.0);
INSERT INTO TICKETTYPE VALUES(2, "ADULT", 15.0);
INSERT INTO TICKETTYPE VALUES(3, "SENIOR", 12.0);



CREATE TABLE IF NOT EXISTS BOOKING (
bookingID int,
PRIMARY KEY (bookingID),
userID int,
FOREIGN KEY (userID) REFERENCES USER (userID),
showtimeID int,
FOREIGN KEY (showtimeID) REFERENCES SHOWTIMES (showID),
noTickets int,
ticketID int,
FOREIGN KEY (ticketID) REFERENCES TICKETTYPE(ticketID),
totalPrice float,
promoID int
);













