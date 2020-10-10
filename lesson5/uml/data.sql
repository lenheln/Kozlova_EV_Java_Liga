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
  PRIMARY KEY (`id`));

/*
   Create table for schoolers
*/
CREATE TABLE `lde-lesson5`.`schoolers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fio` VARCHAR(100) NOT NULL,
  `age` INT NULL,
  `gender` ENUM('male', 'female') NULL,
  `school_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_school_schooler`
    FOREIGN KEY (`school_id`)
    REFERENCES `lde-lesson5`.`school` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    /*
		Создаем таблицу 'education_plan' в которой указано какой предмет какой учитель ведет
        Кросс таблица для учителей и предметов
	*/
    CREATE TABLE `lde-lesson5`.`education_plan` (
  `id_teacher` INT NOT NULL,
  `id_subject` INT NOT NULL,
  PRIMARY KEY (`id_teacher`, `id_subject`),
  CONSTRAINT `teacher_fk`
    FOREIGN KEY (`id_teacher`)
    REFERENCES `lde-lesson5`.`teachers` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `subject_fk`
    FOREIGN KEY (`id_teacher`)
    REFERENCES `lde-lesson5`.`subjects` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
/* 
	Создаем кросс-таблицу 'timetable' в которой указываем учеников и соответсвующие им предметы
*/

CREATE TABLE `lde-lesson5`.`timetable` (
  `id_schooler` INT NOT NULL,
  `id_subject` INT NOT NULL,
  PRIMARY KEY (`id_schooler`, `id_subject`),
  CONSTRAINT `fk_schooler_sub`
    FOREIGN KEY (`id_schooler`)
    REFERENCES `lde-lesson5`.`schoolers` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_sub_schooler`
    FOREIGN KEY (`id_subject`)
    REFERENCES `lde-lesson5`.`subjects` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);