CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_Id VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    password_old1 VARCHAR(255),
    password_old2 VARCHAR(255),
    created DATETIME NOT NULL,
    last_login DATETIME NOT NULL,
    is_active BOOLEAN NOT NULL,
    modified DATETIME,
    token VARCHAR (1000)
);

CREATE TABLE PHONES (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(20) NOT NULL,
    citycode VARCHAR(10),
    countrycode VARCHAR(10),
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(ID)
);

