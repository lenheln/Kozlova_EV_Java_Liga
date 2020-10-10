CREATE TABLE school(
	id serial PRIMARY KEY,
	name text NOT NULL,
	address text
);
COMMENT ON TABLE school IS 'Таблица для школ';

CREATE TYPE genders AS ENUM ('male', 'female');

CREATE TABLE teachers(
	id serial PRIMARY KEY,
	fio text NOT NULL,
	age integer CHECK (age > 18)
	gender genders
	school_id integer REFERENCES school ON DELETE RESTRICT
);
COMMENT ON TABLE  teachers IS 'Таблица для учителей';

CREATE TABLE subjects(
	id serial PRIMARY KEY,
	name text NOT NULL
);
COMMENT ON TABLE subjects IS 'Таблица для предметов';

CREATE TABLE schoolers(
	id serial PRIMARY KEY,
	fio text NOT NULL,
	age integer CHECK (age > 5)
	gender genders
	school_id integer REFERENCES school
);
COMMENT ON TABLE schoolers IS 'Таблица для учеников';

CREATE TABLE teacher_subject(
	id_teacher integer REFERENCES teachers ON DELETE CASCADE,
	id_subject integer REFERENCES subjects ON DELETE CASCADE,
	PRIMARY KEY(id_teacher, id_subject)
);
COMMENT ON TABLE teacher_subject IS 'Кросс-таблица учитель - предмет';

CREATE TABLE schooler_subject(
	id_schooler integer REFERENCES schoolers ON DELETE CASCADE,
	id_subject integer REFERENCES subjects ON DELETE CASCADE,
	PRIMARY KEY(id_schooler, id_subject)
);
COMMENT ON TABLE schooler_subject IS 'Кросс-таблица ученик - предмет';