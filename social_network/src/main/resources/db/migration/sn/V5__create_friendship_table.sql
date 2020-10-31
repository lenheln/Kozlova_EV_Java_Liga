--Создание таблицы описывающей дружбу пользователей
CREATE TABLE IF NOT EXISTS friendship
(
    idUser INTEGER NOT NULL REFERENCES users (id),
    idFriend INTEGER NOT NULL REFERENCES users (id),
    PRIMARY KEY (idUser, idFriend)
);