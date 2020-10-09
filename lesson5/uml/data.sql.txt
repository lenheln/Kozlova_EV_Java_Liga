CREATE SCHEMA `lde-lesson5` ;

/*
   Create table for school 
*/
CREATE TABLE `lde-lesson5`.`school` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `address` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));

/*
   Create table for teachers
*/
CREATE TABLE `lde-lesson5`.`teachers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fio` VARCHAR(100) NOT NULL,
  `age` INT NULL,
  `gender` ENUM('male', 'female') NULL,
  `school_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_school_idx` (`school_id` ASC) VISIBLE,
  CONSTRAINT `fk_school`
    FOREIGN KEY (`school_id`)
    REFERENCES `lde-lesson5`.`school` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

/*
   Create table for subjects
*/
CREATE TABLE `lde-lesson5`.`subjects` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `teacher_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_teacher_idx` (`teacher_id` ASC) VISIBLE,
  CONSTRAINT `fk_teacher`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `lde-lesson5`.`teachers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

/*
   Create table for schoolers
*/
CREATE TABLE `lde-lesson5`.`schoolers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fio` VARCHAR(100) NOT NULL,
  `age` INT NULL,
  `gender` ENUM('male', 'female') NULL,
  `school_id` INT NULL,
  `subject_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_school_idx` (`school_id` ASC) VISIBLE,
  INDEX `fk_subject_idx` (`subject_id` ASC) VISIBLE,
  CONSTRAINT `fk_school_schooler`
    FOREIGN KEY (`school_id`)
    REFERENCES `lde-lesson5`.`school` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `lde-lesson5`.`subjects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

