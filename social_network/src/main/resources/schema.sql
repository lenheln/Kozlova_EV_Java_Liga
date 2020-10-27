-- Создаем поле-перечисление для указания пола пользователя
--CREATE TYPE genders AS ENUM ('male', 'female');

-- Создание таблицы Пользователей
CREATE TABLE IF NOT EXISTS users (
  id        SERIAL          PRIMARY KEY,
  name      TEXT            NOT NULL,
  surname   TEXT            NOT NULL,
  age       INTEGER         CHECK (age > 0) NOT NULL,
  gender    TEXT            NOT NULL,
  interests TEXT,
  city      TEXT
);