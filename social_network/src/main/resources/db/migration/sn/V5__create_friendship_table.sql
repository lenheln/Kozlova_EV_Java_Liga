--Создание таблицы описывающей дружбу пользователей
CREATE TABLE IF NOT EXISTS friendship
(
    iduser INTEGER NOT NULL REFERENCES users (id),
    idfriend INTEGER NOT NULL REFERENCES users (id),
    PRIMARY KEY (idUser, idFriend)
);