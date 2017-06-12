DROP DATABASE IF EXISTS SQUADER;
CREATE DATABASE SQUADER;
USE SQUADER;

CREATE TABLE squaders
(
	id INT AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    first VARCHAR(50),
    last VARCHAR(50),
    email VARCHAR(50),
    phone VARCHAR(15),
    PRIMARY KEY(id)
);
CREATE TABLE images (
	uID INT,
	image BLOB NOT NULL,
    image_name varchar(255),
    FOREIGN KEY(uID) REFERENCES squaders(id)
);
CREATE TABLE work_experience
(
	sID INT,
    job VARCHAR(255),
    duties LONGTEXT,
    yearFROM DATE,
    yearEND DATE,
    monthFROM DATE,
    monthEND DATE,
    FOREIGN KEY(sId) REFERENCES squaders(id)
);
CREATE TABLE personal_info
(
	sID INT,
    school VARCHAR(255),
    gpa VARCHAR(10),
    tagline VARCHAR(255),
    skills LONGTEXT,
    interests LONGTEXT,
    FOREIGN KEY(sID) REFERENCES squaders(id)
);
CREATE TABLE project
(
	id INT AUTO_INCREMENT,
    name VARCHAR(50),
    description LONGTEXT,
    tags LONGTEXT,
    updatedAt TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);
CREATE TABLE project_manager
(
	pmID INT,
    pID INT,
    FOREIGN KEY(pmID) REFERENCES squaders(id),
    FOREIGN KEY(pId) REFERENCES project(id),
	PRIMARY KEY(pmID, pID)
);
CREATE TABLE teams
(
    pID INT,
	uID INT,
    permissions INT,
    roles VARCHAR(255),
    FOREIGN KEY(pID) REFERENCES project(id),
    FOREIGN KEY(uID) REFERENCES squaders(id),
	PRIMARY KEY(pID, uID)
);
CREATE TABLE matches
(
    pID INT,
	uID INT,
	matched BOOLEAN,
    FOREIGN KEY(pID) REFERENCES project(id),
    FOREIGN KEY(uID) REFERENCES squaders(id),
	PRIMARY KEY(pID, uID)
);
INSERT INTO squaders(username, password, first, last, email, phone)
VALUES("mike", "password", "Mike", "Phe", "mike@example.com", "555-555-555");
