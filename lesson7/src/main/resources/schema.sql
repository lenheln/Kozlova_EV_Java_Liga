--*** USER - MSG ***--

CREATE TABLE `lesson7`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `info` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `lesson7`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(200) ,
  `authorId` INT,
  `recieverId` INT,
  `date` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `authorFK_idx` (`authorId` ASC) VISIBLE,
  INDEX `recieverFK_idx` (`recieverId` ASC) VISIBLE,
  CONSTRAINT `authorFK`
    FOREIGN KEY (`authorId`)
    REFERENCES `lesson7`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `recieverFK`
    FOREIGN KEY (`recieverId`)
    REFERENCES `lesson7`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


---- POSTGRES ----
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  surname VARCHAR(45) NOT NULL,
  info VARCHAR(255) NOT NULL
);

CREATE TABLE message (
  id SERIAL PRIMARY KEY,
  message VARCHAR(200) ,
  authorId integer REFERENCES users (id),
  recieverId integer REFERENCES users (id),
  dateTime date
);