-- Создаем поле-перечисление для указания пола пользователя
--CREATE TYPE genders AS ENUM ('male', 'female');
-- Добавить комментарии на столбцы
-- Создание таблицы Пользователей
CREATE TABLE IF NOT EXISTS users
(
  id        SERIAL          PRIMARY KEY,
  name      TEXT            NOT NULL,
  surname   TEXT            NOT NULL,
  age       INTEGER         CHECK (age > 0) NOT NULL,
  gender    TEXT            NOT NULL,
  interests TEXT,
  city      TEXT
);

--Создание таблицы описывающей дружбу пользователей
CREATE TABLE IF NOT EXISTS friends
(
    idUser1 INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    idUser2 INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (idUser1, idUser2)
)