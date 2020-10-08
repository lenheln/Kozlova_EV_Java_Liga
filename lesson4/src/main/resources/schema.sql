CREATE TABLE IF NOT EXISTS Customers
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    EMAIL VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Orders
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    PRICE INT,
    CUSTOMER_ID INT
);

ALTER TABLE Orders ADD FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(id);