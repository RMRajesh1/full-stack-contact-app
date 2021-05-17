
DROP DATABASE IF EXISTS contact_application;

CREATE DATABASE contact_application;

USE contact_application;

CREATE TABLE contact (contact_id VARCHAR(20) NOT NULL, image VARCHAR(350), name VARCHAR(100) NOT NULL, email VARCHAR(100), description VARCHAR(500), date BIGINT NOT NULL, PRIMARY KEY(contact_id));
CREATE TABLE number (number_id VARCHAR(20) NOT NULL, number BIGINT NOT NULL, type INT NOT NULL, contact_id VARCHAR(20) NOT NULL, PRIMARY KEY(number_id), FOREIGN KEY(contact_id) REFERENCES contact(contact_id));

DROP TABLE contact;
DROP TABLE number;






/*

mysql> DESC contact;
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| contact_id  | varchar(20)  | NO   | PRI | NULL    |       |
| image       | varchar(350) | YES  |     | NULL    |       |
| name        | varchar(100) | NO   |     | NULL    |       |
| email       | varchar(100) | YES  |     | NULL    |       |
| description | varchar(500) | YES  |     | NULL    |       |
| date        | bigint(20)   | NO   |     | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
6 rows in set (0.00 sec)

mysql> DESC number;
+------------+-------------+------+-----+---------+-------+
| Field      | Type        | Null | Key | Default | Extra |
+------------+-------------+------+-----+---------+-------+
| number_id  | varchar(20) | NO   | PRI | NULL    |       |
| number     | bigint(20)  | NO   |     | NULL    |       |
| type       | int(11)     | NO   |     | NULL    |       |
| contact_id | varchar(20) | NO   | MUL | NULL    |       |
+------------+-------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

*/
