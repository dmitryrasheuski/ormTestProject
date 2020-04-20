CREATE ROLE test PASSWORD 'test' LOGIN CREATEDB;
CREATE DATABASE test OWNER test;

CREATE TABLE contact_item (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE technology_item (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE gender (
    id INT NOT NULL PRIMARY KEY,
    title VARCHAR(6) NOT NULL
);

CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50) NOT NULL,
    birthday date NOT NULL,
    gender INT NOT NULL REFERENCES gender(id)
);

CREATE TABLE persons_contacts (
    person_id BIGINT NOT NULL REFERENCES person(id),
    contact_item_id INT NOT NULL REFERENCES contact_item(id),
    value VARCHAR(100) NOT NULL
);

CREATE TABLE persons_technologies (
    person_id BIGINT NOT NULL REFERENCES person(id),
    technology_item_id INT NOT NULL REFERENCES technology_item(id)
);

INSERT INTO gender VALUES
   (1, 'male'),
   (2, 'female')
;

INSERT INTO technology_item(title) VALUES
    ('GIT'),
    ('SPRING BOOT'),
    ('HTML'),
    ('JAVA EE'),
    ('JAVA CORE'),
    ('MAVEN'),
    ('REST'),
    ('SPRING')
;

INSERT INTO contact_item(title) VALUES
    ('phone'),
    ('email'),
    ('repository'),
    ('skype'),
    ('other')
;

INSERT INTO person(second_name, first_name, patronymic, birthday, gender) VALUES
    ('Петров', 'Петр', 'Петрович', '12.12.1986', 1),
    ('Иванов', 'Иван', 'Иванович', '4.4.1997', 1),
    ('Морская', 'Мария', 'Васильевна', '7.11.1999', 2)
;

INSERT INTO persons_contacts(person_id, contact_item_id, value) VALUES
    (1, 1, '+375(29)123-45-67'),
    (1, 2, 'petrovich@gmail.com'),
    (1, 3, 'http://github.com/petya'),
    (2, 1, '+375(29)87-65-43'),
    (2, 3, 'http://github.com/vanya'),
    (2, 4, 'ivanko'),
    (3, 1, '+375(29)999-99-99'),
    (3, 5, 'http://linkedin.com/in/mariya/')
;

INSERT INTO persons_technologies(person_id, technology_item_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 4),
    (2, 5),
    (3, 6),
    (3, 7),
    (3, 8)
;