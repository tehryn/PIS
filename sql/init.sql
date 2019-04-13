use pis;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS COMMODITYPRICE;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS COMMODITY;
DROP TABLE IF EXISTS RESERVEDCOMMODITY;
DROP TABLE IF EXISTS RESERVATION;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE USER (
    id INT(6) UNSIGNED AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100),
    role  SMALLINT NOT NULL,
    PRIMARY KEY( id )
);

CREATE TABLE COMMODITY (
    id INT(6) UNSIGNED AUTO_INCREMENT,
    type SMALLINT NOT NULL,
    availability SMALLINT NOT NULL,
    sysid VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(4000),
    PRIMARY KEY( id )
);

CREATE TABLE COMMODITYPRICE (
    id INT(6) UNSIGNED AUTO_INCREMENT,
    currency SMALLINT NOT NULL,
    valuePer SMALLINT NOT NULL,
    value FLOAT NOT NULL,
    commodity INT(6) UNSIGNED NOT NULL,
    FOREIGN KEY (commodity) REFERENCES COMMODITY(id),
    UNIQUE( currency, commodity ),
    PRIMARY KEY( id )
);

CREATE TABLE RESERVATION (
    id INT(6) UNSIGNED AUTO_INCREMENT,
    user INT(6) UNSIGNED,
    status SMALLINT NOT NULL,
    FOREIGN KEY (user) REFERENCES USER(id),
    PRIMARY KEY( id )
);

CREATE TABLE RESERVEDCOMMODITY (
    id INT(6) UNSIGNED AUTO_INCREMENT,
    frm DATETIME NOT NULL,
    until DATETIME NOT NULL,
    RESERVATION INT(6) UNSIGNED NOT NULL,
    commodity INT(6) UNSIGNED NOT NULL,
    FOREIGN KEY (RESERVATION) REFERENCES RESERVATION(id),
    FOREIGN KEY (commodity) REFERENCES COMMODITY(id),
    PRIMARY KEY( id )
);

INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Jiri', 'Matejka', 'matej@pis.cz', '123', 4);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Petr', 'Recepcni', 'petr@pis.cz', '123', 2);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Gargamel', 'BOSS', 'gargame@pis.cz', '123', 3);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Franta', 'Uzivatel', 'franta@pis.cz', '123', 1);

INSERT INTO COMMODITY (type, availability, sysid)
VALUES (0, 0, 'P101');
INSERT INTO COMMODITY (type, availability, sysid)
VALUES (0, 0, 'P102');
INSERT INTO COMMODITY (type, availability, sysid)
VALUES (0, 1, 'P103');
INSERT INTO COMMODITY (type, availability, sysid)
VALUES (1, 0, 'Masaz');
INSERT INTO COMMODITY (type, availability, sysid)
VALUES (1, 1, 'VIP Masaz');
