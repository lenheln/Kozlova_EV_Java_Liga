--Создание таблицы описывающей дружбу пользователей
--CREATE TABLE IF NOT EXISTS social_network.friendship
CREATE TABLE IF NOT EXISTS friendship
(
    idUser INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    idFriend INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (idUser, idFriend)
);