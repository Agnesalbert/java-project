-- Create the AddressBook database
CREATE DATABASE AddressBook;

-- Use the AddressBook database
USE AddressBook;

-- Check SQL version
SELECT VERSION();

-- Create the Addresses table
CREATE TABLE Addresses (
    AddressID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(254) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Department VARCHAR(50) NOT NULL
);

-- Insert records into the Addresses table
INSERT INTO Addresses (FirstName, LastName, Email, PhoneNumber, Address, Department)
VALUES 
    ('Mike', 'Green', 'demo1@deitel.com', '555-5555', '123 Main St', 'Mkt'),
    ('Mary', 'Brown', 'demo2@deitel.com', '555-1234', '456 Elm St', 'HR');

-- Select specific columns from the Addresses table
SELECT FirstName, LastName, Email, PhoneNumber, Address, Department FROM Addresses;

-- Select all records where LastName is 'Green', ordered by LastName and FirstName
SELECT * FROM Addresses WHERE LastName LIKE 'Green' ORDER BY LastName, FirstName;

-- Show all tables in the current database
SHOW TABLES;
