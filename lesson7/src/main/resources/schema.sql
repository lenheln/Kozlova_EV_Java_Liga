CREATE TABLE `lesson7`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `info` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `lesson7`.`dialog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`));


--Join Table of Users and Dialogs
CREATE TABLE `lesson7`.`userDialog` (
  `dialog_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`dialog_id`, `user_id`),
  INDEX `user_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `dialog_fk`
    FOREIGN KEY (`dialog_id`)
    REFERENCES `lesson7`.`dialog` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `lesson7`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

    CREATE TABLE `lesson7`.`massage` (
      `id` INT NOT NULL AUTO_INCREMENT,
      `massage` VARCHAR(300) NOT NULL,
      `authorId` INT NULL,
      `recieverId` INT NULL,
      `time` DATETIME,
      `dialogId` INT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `authorIdFk`
        FOREIGN KEY (`id`)
        REFERENCES `lesson7`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
      CONSTRAINT `recieverIdFk`
        FOREIGN KEY (`id`)
        REFERENCES `lesson7`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
      CONSTRAINT `dialogIdFk`
        FOREIGN KEY (`id`)
        REFERENCES `lesson7`.`dialog` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);


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
