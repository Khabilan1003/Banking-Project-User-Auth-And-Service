CREATE TABLE B2_Branch_TBL (
    branchCode INT NOT NULL,
    branchName VARCHAR(255),
    ifscCode VARCHAR(50),
    PRIMARY KEY (branchCode)
);
CREATE TABLE B2_Role_TBL (
    roleId INT NOT NULL AUTO_INCREMENT,
    roleName VARCHAR(100),
    PRIMARY KEY (roleId)
);
CREATE TABLE B2_User_TBL (
    userId INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    username VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    branchCode INT,
    phoneNumber VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    PRIMARY KEY (userId),
    FOREIGN KEY (branchCode) REFERENCES B2_Branch_TBL(branchCode)
);
CREATE TABLE B2_UserRoles_TBL (
    userId INT NOT NULL ,
    roleId INT NOT NULL,
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId) REFERENCES B2_User_TBL(userId),
    FOREIGN KEY (roleId) REFERENCES B2_Role_TBL(roleId)
);

insert into b2_branch_tbl values (218 , 'KOMARAPALAYAM' , 'HDFC0000218');
insert into b2_branch_tbl values (219 , 'BHAVANI' , 'HDFC0000219');
insert into b2_branch_tbl values (220 , 'CHENNAI' , 'HDFC0000220');

insert into b2_role_tbl (roleName) value ('ROLE_MANAGER');
insert into b2_role_tbl (roleName) value ('ROLE_ASSISTANT_MANAGER');
insert into b2_role_tbl (roleName) value ('ROLE_CLERK');
insert into b2_role_tbl (roleName) value ('ROLE_ADMIN');
insert into b2_role_tbl (roleName) value ('ROLE_CLERK_MAKER');
insert into b2_role_tbl (roleName) value ('ROLE_CLERK_TELLER');