-- Создание таблицы клиентов
CREATE TABLE IF NOT EXISTS Customers
(
    ID      INT             PRIMARY KEY AUTO_INCREMENT,
    NAME    VARCHAR(100)    NOT NULL    COMMENT 'Имя клиента',
    EMAIL   VARCHAR(100)    NOT NULL
);

-- Создание таблицы заказов
CREATE TABLE IF NOT EXISTS Orders
(
    ID              INT             PRIMARY KEY     AUTO_INCREMENT,
    NAME            VARCHAR(255)    NOT NULL        COMMENT 'Название заказа',
    PRICE           INT             NOT NULL        COMMENT 'Стоимость заказа',
    CUSTOMER_ID     INT             NOT NULL        COMMENT 'ID клиента'
);

ALTER TABLE Orders ADD FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(id);