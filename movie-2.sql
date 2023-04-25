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
billingAddressLine1 varchar(255),
billingAddressLine2 varchar(255),
billingZip varchar(255),
billingCity varchar(255),
billingState varchar(255),
shippingAddressLine1 varchar(255),
shippingAddressLine2 varchar(255),
shippingZip varchar(255),
shippingCity varchar(255),
shippingState varchar(255),
ACTIVE int,
FOREIGN KEY (ACTIVE) REFERENCES ACTIVE (activeID),
confirm int,
cardnum varchar(255),
securitynum varchar(255),
expmonth int,
expdate int,
enabledPromotion int
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
movieID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (movieID),
title varchar(255) NOT NULL,
casting varchar(255),
genre varchar(255) NOT NULL,
director varchar(255) NOT NULL,
producer varchar(255),
duration varchar(255) NOT NULL,
synopsis text,
display varchar(255) NOT NULL,
trailerPicture varchar(255) NOT NULL,
trailerVideo varchar(255) NOT NULL,
review float NOT NULL,
ratingID int NOT NULL,
FOREIGN KEY (ratingID) REFERENCES USRating (ratingID)
);

INSERT INTO MOVIE VALUES(1, "80 for Brady", "Lily Tomlin, Jane Fonda, Rita Moreno, Sally Field", "Comedy, Drama, Sport", "Kyle Marvin", "Tom Brady", "1h 38m", "Four best friends live life to the fullest when they\nembark on a wild trip to see their hero, Tom Brady,\nplay in the 2017 Super Bowl.", "Now Playing", "images/movie/80 for brady.jpg", "https://www.youtube.com/watch?v=-UeGXB2NjR8", 5.8, 2);
INSERT INTO MOVIE VALUES(2, "Knock at the Cabin", "Dave Bautista, Jonathan Groff, Ben Aldridge", "Horror, Mystery, Thriller", "M. Night Shyamalan", "M. Night Shyamalan", "1h 40m", "While vacationing at a remote cabin in the woods, a young girl\nand her parents are taken hostage by four armed strangers who\ndemand they make an unthinkable choice to avert the apocalypse.\nConfused, scared and with limited access to the outside world,\nthe family must decide what they believe before all is lost.", "Now Playing", "images/movie/knock at the cabin.jpg", "https://www.youtube.com/watch?v=gv_QhoUy-xc", 6.1, 3);
INSERT INTO MOVIE VALUES(3, "Puss in Boots: The Last Wish", "Antonio Banderas, Salma Hayek, Harvey Guillén", "Animation, Comedy", "Joel Crawford", "Andrew Adamson", "1h 42m", "Puss in Boots discovers that his passion for adventure has taken\nits toll when he learns that he has burnt through eight of his\nnine lives. Puss sets out on an epic journey to find the mythical\nLast Wish and restore his nine lives.", "Now Playing", "images/movie/puss in boots.jpg", "https://www.youtube.com/watch?v=RqrXhwS33yc", 7.9, 1);
INSERT INTO MOVIE VALUES(4, "Ant-Man and the Wasp: Quantumania", "Paul Rudd, Evangeline Lilly, Michael Douglas", "Action, Adventure, Comedy", "Peyton Reed", "Victoria Alonso", "2h 4m", "Ant-Man and the Wasp find themselves exploring the Quantum Realm,\ninteracting with strange new creatures and embarking on an adventure\nthat pushes them beyond the limits of what they thought was\npossible.", "Now Playing", "images/movie/quantumania.jpg", "https://www.youtube.com/watch?v=ZlNFpri-Y40", 6.4, 2);
INSERT INTO MOVIE VALUES(5, "Avatar: The Way of the Water", "Sam Worthington, Zoe Saldana, Sigourney Weaver", "Action, Adventure, Fantasy", "James Cameron", "Richard Baneham", "3h 12m", "Jake Sully and Ney'tiri have formed a family and are doing everything\nto stay together. However, they must leave their home and explore the\nregions of Pandora. When an ancient threat resurfaces, Jake must\nfight a difficult war against the humans.", "Now Playing", "images/movie/avatar the way of the water.jpg", "https://www.youtube.com/watch?v=d6xdV8VAqBY", 7.7, 2);
INSERT INTO MOVIE VALUES(6, "A Man Called Otto", "Tom Hanks, Mariana Treviño, Rachel Keller", "Comedy, Drama", "Marc Forster", "Neda Backman", "2h 6m", "When a lively young family moves in next door, grumpy widower Otto\nAnderson meets his match in a quick-witted, pregnant woman named\nMarisol, leading to an unlikely friendship that turns his world\nupside down.", "Now Playing", "images/movie/a man called otto.jpg", "https://www.youtube.com/watch?v=eFYUX9l-m5I", 7.4, 2);
INSERT INTO MOVIE VALUES(7, "M3GAN", "Allison Williams, Violet McGraw, Ronny Chieng", "Horror, Sci-Fi, Thriller", "Gerard Johnstone", "Jason Blum", "1h 42m", "M3GAN is a marvel of artificial intelligence, a lifelike doll\nthat's programmed to be a child's greatest companion and a parent's\ngreatest ally. Designed by Gemma, a brilliant roboticist, M3GAN can\nlisten, watch and learn as it plays the role of friend and teacher,\nplaymate and protector. When Gemma becomes the unexpected caretaker\nof her 8-year-old niece, she decides to give the girl an M3GAN\nprototype, a decision that leads to unimaginable consequences.", "Now Playing", "images/movie/MEGAN.jpg", "https://www.youtube.com/watch?v=BRb4U99OU80", 6.4, 2);
INSERT INTO MOVIE VALUES(8, "Pathaan", "Shah Rukh Khan, Deepika Padukone, John Abraham", "Action, Adventure, Thriller", "Siddharth Anand", "Aditya Chopra", "2h 26m", "An Indian spy battles against the leader of a gang of mercenaries\nwho have a heinous plot for his homeland.", "Now Playing", "images/movie/Pathaan.jpg", "https://www.youtube.com/watch?v=nDHsBUbivz8", 6.0, 3);
INSERT INTO MOVIE VALUES(9, "Magic Mike's Last Dance", "Channing Tatum, Salma Hayek, Ayub Khan-Din", "Comedy, Drama", "Steven Soderbergh", "Julie M. Anderson", "1h 52m", "Mike Lane takes to the stage once again when a business deal\nthat went bust leaves him broke and bartending in Florida. Hoping\nfor one last hurrah, Mike heads to London with a wealthy socialite\nwho lures him with an offer he can't refuse -- and an agenda all\nher own. With everything on the line, he soon finds himself trying\nto whip a hot new roster of talented dancers into shape.", "Now Playing", "images/movie/magic mike.jpg", "https://www.youtube.com/watch?v=pBIGdw-BRxw", 5.3, 3);
INSERT INTO MOVIE VALUES(10, "The Little Mermaid", "Halle Bailey, Jonah Hauer-King, Melissa McCarthy", "Adventure, Family, Fantasy", "Rob Marshall", "Russell Alan", "N/A", "The youngest of King Triton's daughters, Ariel is a beautiful\nand spirited young mermaid with a thirst for adventure. Longing\nto find out more about the world beyond the sea, Ariel visits the\nsurface and falls for the dashing Prince Eric. Following her heart,\nshe makes a deal with the evil sea witch, Ursula, to experience\nlife on land.", "Release Date: May 26, 2023", "images/upcoming/the little mermaid.jpg", "https://www.youtube.com/watch?v=pHZdnopVgTI", 0, 1);
INSERT INTO MOVIE VALUES(11, "Luther: The Fallen Sun", "Idras Elba, Cynthia Erivo, Andy Serkis", "Crime, Drama, Mystery", "Jamie Payne", "Peter Chernin", "2h 9m", "While vacationing at a remote cabin in the woods, a young girl\nand her parents are taken hostage by four armed strangers who\ndemand they make an unthinkable choice to avert the apocalypse.\nConfused, scared and with limited access to the outside world,\nthe family must decide what they believe before all is lost.", "Now Playing", "images/upcoming/luther the fallen sun.jpg", "https://www.youtube.com/watch?v=EGK5qtXuc1Q", 6.4, 3);
INSERT INTO MOVIE VALUES(12, "Wonka", "Timothée Chalamet, Olivia Colman, Sally Hawkins", "Adventure, Comedy, Family", "Paul King", "Rosie Alison", "N/A", "The story focuses on a young Willy Wonka and how he met the\nOompa-Loompas on one of his earliest adventures.", "Release Date: December 15, 2023", "images/upcoming/wonka.jpg", "https://www.youtube.com/watch?v=Kw4Yu7SSX5M", 0, 2);
INSERT INTO MOVIE VALUES(13, "The Super Mario Bros. Movie", "Chris Pratt, Anya Taylor-Joy, Charlie Day, Jack Black", "Animation, Adventure, Comedy", "Aaron Horvath", "Brett Hoffman", "1h 32m", "A plumber named Mario travels through an underground labyrinth\nwith his brother, Luigi, trying to save a captured princess.", "Now Playing", "images/upcoming/the super mario bros movie.jpg", "https://www.youtube.com/watch?v=TnGl01FkMMo", 7.4, 1);
INSERT INTO MOVIE VALUES(14, "Guardians of the Galaxy Vol. 3", "Chris Pratt, Zoe Saldana, Dave Bautista", "Action, Adventure, Comedy", "James Gunn", "Victoria Alonso", "2h 30m", "Still reeling from the loss of Gamora, Peter Quill rallies his\nteam to defend the universe and one of their own - a mission that\ncould mean the end of the Guardians if not successful.", "Release Date: May 5, 2023", "images/upcoming/guardians of the galaxy vol 3.jpg", "https://www.youtube.com/watch?v=br4CsE-w8pA", 0, 2);
INSERT INTO MOVIE VALUES(15, "Barbie", "Margot Robbie, Ryan Gosling, Ariana Greenblatt", "Adventure, Comedy, Fantasy", "Greta Gerwig", "Tom Ackerley", "N/A", "After being expelled from Barbieland for being a less than\nperfect-looking doll, Barbie sets off for the human world to find\ntrue happiness.", "Release Date: July 21, 2023", "images/upcoming/barbie.jpg", "https://www.youtube.com/watch?v=NELUoe-zxwM", 0, 2);
INSERT INTO MOVIE VALUES(16, "The Portable Door", "Patrick Gibson, Christoph Waltz, Sam Neill", "Fantasy", "Jeffrey Walker", "Brian Beckmann", "1h 56m", "A man lands an internship at a mysterious London firm with\nunconventional employees, including the charismatic CEO who is\nincorporating modern corporate strategy into ancient magical\npractices.", "Now Playing", "images/upcoming/the portable door.jpg", "https://www.youtube.com/watch?v=9Y0GTdd_cNs&t=1s", 5.9, 2);

CREATE TABLE IF NOT EXISTS AUDITORIUM (
audID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY(audID), 
audName varchar(255),
noOfSeats int
);

INSERT INTO AUDITORIUM VALUES(1,'Viewing Center 1',10); 
INSERT INTO AUDITORIUM VALUES(2,"Viewing Center 2",10);
INSERT INTO AUDITORIUM VALUES(3,"Viewing Center 3",10);


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
movieShowID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (movieShowID), 
showID int,
FOREIGN KEY (showID) REFERENCES SHOWTIMES (showID),
movieID int,
FOREIGN KEY (movieID) REFERENCES MOVIE (movieID),
auditoriumID int,
FOREIGN KEY (auditoriumID) REFERENCES AUDITORIUM (AudID),
availableSeats int,
showStart DATE,
timeFilled varchar(255)
);

CREATE TABLE IF NOT EXISTS AUDSEATS (
seatID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (seatID),
audID int,
FOREIGN KEY (audID) REFERENCES AUDITORIUM (audID),
sectionRow char,
sectionCol int
);

CREATE TABLE IF NOT EXISTS MOVIESHOWSEATS (
movieSeatID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (movieSeatID),
movieShowID int,
FOREIGN KEY (movieShowID) REFERENCES MOVIESHOW (movieShowID),
seatID int,
FOREIGN KEY (seatID) REFERENCES AUDSEATS (seatID),
available int DEFAULT 1
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
bookingID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (bookingID),
userID int,
FOREIGN KEY (userID) REFERENCES USER (userID),
movieShowID int,
FOREIGN KEY (movieShowID) REFERENCES MOVIESHOW (movieShowID),
noChildTickets int,
noAdultTickets int,
noSeniorTickets int,
totalPrice float,
promoID int
);

CREATE TABLE IF NOT EXISTS PROMOTIONS (
promotionID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (promotionID),
promotionCode varChar(255),
percentOff int
);

INSERT INTO PROMOTIONS VALUES(1, "NOPROMO",0);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (1,1,'A',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (2,1,'A',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (3,1,'B',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (4,1,'B',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (5,1,'C',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (6,1,'C',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (7,1,'D',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (8,1,'D',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (9,1,'E',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (10,1,'E',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (11,2,'A',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (12,2,'A',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (13,2,'B',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (14,2,'B',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (15,2,'C',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (16,2,'C',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (17,2,'D',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (18,2,'D',2);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (19,2,'E',1);
INSERT INTO AUDSEATS (`seatID`,`audID`,`sectionRow`,`sectionCol`) VALUES (20,2,'E',2);




