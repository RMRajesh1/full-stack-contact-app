DROP DATABASE IF EXISTS contact_application;

CREATE DATABASE contact_application;

\c contact_application;

CREATE TABLE users (USER_ID SERIAL NOT NULL, NAME VARCHAR(100) NOT NULL, EMAIL VARCHAR(100) NOT NULL, PASSWORD VARCHAR(15) NOT NULL, CREATED_DATE BIGINT NOT NULL, PRIMARY KEY (USER_ID), UNIQUE(EMAIL));
CREATE TABLE contact (CONTACT_ID SERIAL NOT NULL, IMAGE VARCHAR(350), NAME VARCHAR(100) NOT NULL, EMAIL VARCHAR(100), DESCRIPTION VARCHAR(500), DATE BIGINT NOT NULL, PRIMARY KEY(CONTACT_ID));
CREATE TABLE number (NUMBER_ID SERIAL NOT NULL, NUMBER BIGINT NOT NULL, TYPE INT NOT NULL, CONTACT_ID SERIAL NOT NULL, PRIMARY KEY(NUMBER_ID), FOREIGN KEY(CONTACT_ID) REFERENCES contact(CONTACT_ID));


/*

contact_application=# \d users;
                                         Table "public.users"
    Column    |          Type          | Collation | Nullable |                Default                 
--------------+------------------------+-----------+----------+----------------------------------------
 user_id      | integer                |           | not null | nextval('users_user_id_seq'::regclass)
 name         | character varying(100) |           | not null | 
 email        | character varying(100) |           | not null | 
 password     | character varying(15)  |           | not null | 
 created_date | bigint                 |           | not null | 
Indexes:
    "users_pkey" PRIMARY KEY, btree (user_id)

contact_application=# \d contact;
                                          Table "public.contact"
   Column    |          Type          | Collation | Nullable |                   Default                   
-------------+------------------------+-----------+----------+---------------------------------------------
 contact_id  | integer                |           | not null | nextval('contact_contact_id_seq'::regclass)
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
   Column   |  Type   | Collation | Nullable |                  Default                   
------------+---------+-----------+----------+--------------------------------------------
 number_id  | integer |           | not null | nextval('number_number_id_seq'::regclass)
 number     | bigint  |           | not null | 
 type       | integer |           | not null | 
 contact_id | integer |           | not null | nextval('number_contact_id_seq'::regclass)
Indexes:
    "number_pkey" PRIMARY KEY, btree (number_id)
Foreign-key constraints:
    "number_contact_id_fkey" FOREIGN KEY (contact_id) REFERENCES contact(contact_id)

*/
