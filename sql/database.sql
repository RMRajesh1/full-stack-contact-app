DROP DATABASE IF EXISTS contact_application;

CREATE DATABASE contact_application;

\c contact_application;

CREATE TABLE contact (CONTACT_ID VARCHAR(20) NOT NULL, IMAGE VARCHAR(350), NAME VARCHAR(100) NOT NULL, EMAIL VARCHAR(100), DESCRIPTION VARCHAR(500), DATE BIGINT NOT NULL, PRIMARY KEY(CONTACT_ID));
CREATE TABLE number (NUMBER_ID VARCHAR(20) NOT NULL, NUMBER BIGINT NOT NULL, TYPE INT NOT NULL, CONTACT_ID VARCHAR(20) NOT NULL, PRIMARY KEY(NUMBER_ID), FOREIGN KEY(CONTACT_ID) REFERENCES contact(CONTACT_ID));

INSERT INTO contact VALUES ('kglo4jmd', '/contact-app/home/assets/images/avatar-370-456322-512.webp', 'MyContact', 'my-avatar@mail.com', 'Describe!', 1621298657973);
INSERT INTO number VALUES ('q75pvk6c', 0987654321, 0, 'kglo4jmd');
INSERT INTO number VALUES ('a2o6kil0', 1234567890, 1, 'kglo4jmd');

INSERT INTO contact VALUES ('q2kftmhs', '/contact-app/home/assets/images/woman-sporty-brunette.webp', 'Lady', 'lady@mail.com', 'Describe about lady!', 1621299079796);
INSERT INTO number VALUES ('v7kp9h9v', 6547389201, 2, 'q2kftmhs');
INSERT INTO number VALUES ('47ks04ah', 0129384756, 3, 'q2kftmhs');

/*

contact_application=# \d contact;
                        Table "public.contact"
   Column    |          Type          | Collation | Nullable | Default 
-------------+------------------------+-----------+----------+---------
 contact_id  | character varying(20)  |           | not null | 
 image       | character varying(350) |           |          | 
 name        | character varying(100) |           | not null | 
 email       | character varying(100) |           |          | 
 description | character varying(500) |           |          | 
 date        | bigint                 |           | not null | 
Indexes:
    "contact_pkey" PRIMARY KEY, btree (contact_id)
Referenced by:
    TABLE "number" CONSTRAINT "number_contact_id_fkey" FOREIGN KEY (contact_id) REFERENCES contact(contact_id)

contact_application=# \d number;
                        Table "public.number"
   Column   |         Type          | Collation | Nullable | Default 
------------+-----------------------+-----------+----------+---------
 number_id  | character varying(20) |           | not null | 
 number     | bigint                |           | not null | 
 type       | integer               |           | not null | 
 contact_id | character varying(20) |           | not null | 
Indexes:
    "number_pkey" PRIMARY KEY, btree (number_id)
Foreign-key constraints:
    "number_contact_id_fkey" FOREIGN KEY (contact_id) REFERENCES contact(contact_id)

*/
