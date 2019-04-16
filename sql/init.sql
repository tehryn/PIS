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

SET NAMES utf8mb4;
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Jiří', 'Matějka', 'matej@pis.cz', '123', 4);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Petr', 'Novák', 'petr@pis.cz', '123', 2);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Gargamel', 'Vysoký', 'gargame@pis.cz', '123', 3);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Franta', 'Prostý', 'franta@pis.cz', '123', 1);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Jan', 'Novotný', 'honza@pis.cz', '123', 1);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Jana', 'Novotná', 'jana@pis.cz', '123', 1);
INSERT INTO USER (firstname, lastname, email, password, role)
VALUES ('Pavel', 'Dvorský', 'pavel@pis.cz', '123', 0);

INSERT INTO COMMODITY (type, availability, sysid, description)
VALUES (0, 0, 'P101', 'Pokoj s manželskou postelí, televizí, internetovou přípojkou a sprchovým koutem.');
INSERT INTO COMMODITY (type, availability, sysid, description)
VALUES (0, 0, 'P102', 'Pokoj s jednou postelí, televizí, internetovou přípojkou a sprchovým koutem.');
INSERT INTO COMMODITY (type, availability, sysid, description)
VALUES (0, 1, 'P103', 'Pokoj s jednou postelí a internetovou přípojkou.');
INSERT INTO COMMODITY (type, availability, sysid, description)
VALUES (1, 0, 'Masáž', 'Masáž lávovými kameny,');
INSERT INTO COMMODITY (type, availability, sysid, description)
VALUES (1, 1, 'Bowlingová dráha', 'Bowlingová dráha určená maximálně pro 5 hráčů.');
INSERT INTO COMMODITY (type, availability, sysid, description)
VALUES (1, 0, 'Kulečníkový stůl', 'Kulečníkový stůl maximálně pro 2 hráče.');

INSERT INTO COMMODITYPRICE (currency, valuePer, value, commodity)
VALUES(0, 3, 299.99, 1);
INSERT INTO COMMODITYPRICE (currency, valuePer, value, commodity)
VALUES(0, 3, 399.99, 2);
INSERT INTO COMMODITYPRICE (currency, valuePer, value, commodity)
VALUES(0, 3, 149.99, 3);
INSERT INTO COMMODITYPRICE (currency, valuePer, value, commodity)
VALUES(0, 2, 450.0, 4);
INSERT INTO COMMODITYPRICE (currency, valuePer, value, commodity)
VALUES(0, 2, 500.0, 5);
INSERT INTO COMMODITYPRICE (currency, valuePer, value, commodity)
VALUES(0, 2, 300.0, 6);

INSERT INTO RESERVATION (user, status)
VALUES (4, 1);
INSERT INTO RESERVATION (user, status)
VALUES (4, 0);
INSERT INTO RESERVATION (user, status)
VALUES (4, 3);
INSERT INTO RESERVATION (user, status)
VALUES (5, 4);
INSERT INTO RESERVATION (user, status)
VALUES (6, 0);
INSERT INTO RESERVATION (user, status)
VALUES (7, 0);

INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2018-12-01 00:00:00', '2018-12-02 00:00:00', 1, 1 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2018-12-01 20:00:00', '2018-12-01 22:00:00', 1, 6 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2018-12-01 23:00:00', '2018-12-02 23:00:00', 1, 5 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2019-12-01 00:00:00', '2019-12-02 00:00:00', 2, 1 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2018-12-01 18:30:00', '2018-12-01 21:00:00', 2, 6 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2019-12-01 21:00:00', '2019-12-02 22:00:00', 3, 5 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2019-12-01 21:00:00', '2019-12-02 22:00:00', 3, 1 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2019-12-01 00:00:00', '2019-12-02 00:00:00', 4, 2 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2019-12-02 00:00:00', '2019-12-03 00:00:00', 5, 1 );
INSERT INTO RESERVEDCOMMODITY (frm, until, reservation, commodity)
VALUES ('2019-12-02 00:00:00', '2019-12-03 00:00:00', 6, 2 );
