CREATE DATABASE IF NOT EXISTS encuesta CHARACTER SET latin1 COLLATE latin1_swedish_ci;

USE encuesta;

DROP TABLE IF EXISTS persona;

CREATE TABLE persona
(idpersona INT auto_increment PRIMARY KEY,
 nombres VARCHAR(30) NOT NULL,
 apellidos VARCHAR(30) NOT NULL,
 edad INT NOT NULL,
 lenguaje VARCHAR(30) NOT NULL
 )ENGINE=INNODB;