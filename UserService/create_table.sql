CREATE TABLE Branch (
    branchCode INT NOT NULL,
    branchName VARCHAR(255),
    ifscCode VARCHAR(50),
    PRIMARY KEY (branchCode)
);

CREATE TABLE Role (
    roleId INT NOT NULL AUTO_INCREMENT,
    roleName VARCHAR(100),
    PRIMARY KEY (roleId)
);

CREATE TABLE User (
    userId INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    username VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    branchCode INT,
    phoneNumber VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    PRIMARY KEY (userId),
    FOREIGN KEY (branchCode) REFERENCES Branch(branchCode)
);

CREATE TABLE UserRoles (
    userId INT NOT NULL ,
    roleId INT NOT NULL,
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId) REFERENCES User(userId),
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);