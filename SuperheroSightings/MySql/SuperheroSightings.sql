-- Ravi Udit AMAZON PART 2 - April 2022, Superhero Sightings

-- Drop database if it exists and create a new one. 

DROP DATABASE IF EXISTS RaviUditSuperheroSightings;

CREATE DATABASE RaviUditSuperheroSightings;
USE RaviUditSuperheroSightings;

CREATE TABLE Superpower(
	
    SuperPowerID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    SuperPowerName VARCHAR(15) NOT NULL,
    SuperPowerDesc VARCHAR(50) NOT NULL

);

CREATE TABLE Superhero(

	SuperID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    SuperName VARCHAR(12) NOT NULL,
    IsHero BOOL NOT NULL,
    
    SuperPowerID INT NOT NULL, 
    FOREIGN KEY fk_SuperHero_SuperPowerID(SuperPowerID)
		REFERENCES SuperPower(SuperPowerID)

);

CREATE TABLE Location(
	
	LocationID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    LocationName VARCHAR(20) NOT NULL, 
    LocationDesc VARCHAR(50) NOT NULL, 
    LocationAddress VARCHAR(100) NOT NULL,
    LocationLat DECIMAL(6,4),
    LocationLong DECIMAL(7,4)

);

CREATE TABLE Team(

	TeamID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    TeamName VARCHAR(25) NOT NULL, 
    TeamDesc VARCHAR(50) NOT NULL,
    TeamContactInfo VARCHAR(50) NOT NULL,
    TeamAddress VARCHAR(150) NOT NULL

);

CREATE TABLE TeamMembers(

    SuperID INT NOT NULL,
    TeamID INT NOT NULL,
    
    PRIMARY KEY pk_TeamMembers (SuperID, TeamID),
	FOREIGN KEY fk_TeamMembers_SuperID(SuperID)
		REFERENCES Superhero(SuperID),
	FOREIGN KEY fk_TeamMembers_TeamID(TeamID)
		REFERENCES Team(TeamID)

);

CREATE TABLE Sighting(

	SightingID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    SightingDate DATETIME, 
    SuperID INT NOT NULL,
    LocationID INT NOT NULL, 
    
    FOREIGN KEY fk_Sighting_SuperID(SuperID)
		REFERENCES Superhero(SuperID),
	FOREIGN KEY fk_Sighting_LocationID(LocationID)
		REFERENCES Location(LocationID)
);
