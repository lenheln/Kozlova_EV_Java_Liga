-- Создаем поле-перечисление для указания пола пользователя
--CREATE TYPE genders AS ENUM ('male', 'female');

-- Создание таблицы Пользователей
--CREATE TABLE users (
--  id        SERIAL          PRIMARY KEY,
--  name      VARCHAR(45)     NOT NULL                    COMMENT 'Имя',
--  surname   VARCHAR(45)     NOT NULL                    COMMENT 'Фамилия',
--  age       INTEGER         CHECK (age > 0) NOT NULL    COMMENT 'Возраст',
----  gender    genders         NOT NULL                    COMMENT 'Пол',
--  interests VARCHAR(512)                                COMMENT 'Интересы',
--  city      VARCHAR(45)                                 COMMENT 'Город',
--);

CREATE TABLE IF NOT EXISTS users (
  id        SERIAL          PRIMARY KEY,
  name      VARCHAR(45)     NOT NULL,
  surname   VARCHAR(45)     NOT NULL,
  age       INTEGER         CHECK (age > 0) NOT NULL,
  gender    VARCHAR(45)     NOT NULL,
  interests VARCHAR(512) ,
  city      VARCHAR(45)
);