-- Создание таблицы городов
CREATE TABLE IF NOT EXISTS cities
(
  id        SERIAL              PRIMARY KEY,
  name      VARCHAR(256)        NOT NULL,
  regionId  INTEGER             REFERENCES regions (id)
);