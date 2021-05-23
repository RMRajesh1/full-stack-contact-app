DROP DATABASE IF EXISTS contact_application;

CREATE DATABASE contact_application;

\c contact_application;

CREATE TABLE users (USER_ID VARCHAR(40) NOT NULL, NAME VARCHAR(100) NOT NULL, EMAIL VARCHAR(100) NOT NULL, PASSWORD VARCHAR(15) NOT NULL, CREATED_DATE BIGINT NOT NULL, PRIMARY KEY (USER_ID), UNIQUE(EMAIL));
CREATE TABLE contact (CONTACT_ID VARCHAR(40) NOT NULL, IMAGE VARCHAR(350), NAME VARCHAR(100) NOT NULL, EMAIL VARCHAR(100), DESCRIPTION VARCHAR(500), DATE BIGINT NOT NULL, USER_ID VARCHAR(40) NOT NULL, PRIMARY KEY(CONTACT_ID), FOREIGN KEY(USER_ID) REFERENCES users(USER_ID));
CREATE TABLE number (NUMBER_ID VARCHAR(40) NOT NULL, NUMBER BIGINT NOT NULL, TYPE INT NOT NULL, CONTACT_ID VARCHAR(40) NOT NULL, USER_ID VARCHAR(40) NOT NULL, PRIMARY KEY(NUMBER_ID), FOREIGN KEY(CONTACT_ID) REFERENCES contact(CONTACT_ID), FOREIGN KEY(USER_ID) REFERENCES users(USER_ID));


/*

contact_application=# \d users;
                          Table "public.users"
    Column    |          Type          | Collation | Nullable | Default 
--------------+------------------------+-----------+----------+---------
 user_id      | character varying(40)  |           | not null | 
 name         | character varying(100) |           | not null | 
 email        | character varying(100) |           | not null | 
 password     | character varying(15)  |           | not null | 
 created_date | bigint                 |           | not null | 
Indexes:
    "users_pkey" PRIMARY KEY, btree (user_id)
    "users_email_key" UNIQUE CONSTRAINT, btree (email)
Referenced by:
    TABLE "contact" CONSTRAINT "contact_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(user_id)
    TABLE "number" CONSTRAINT "number_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(user_id)

contact_application=# \d contact;
                        Table "public.contact"
   Column    |          Type          | Collation | Nullable | Default 
-------------+------------------------+-----------+----------+---------
 contact_id  | character varying(40)  |           | not null | 
 image       | character varying(350) |           |          | 
 name        | character varying(100) |           | not null | 
 email       | character varying(100) |           |          | 
 description | character varying(500) |           |          | 
 date        | bigint                 |           | not null | 
 user_id     | character varying(40)  |           | not null | 
Indexes:
    "contact_pkey" PRIMARY KEY, btree (contact_id)
Foreign-key constraints:
    "contact_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(user_id)
Referenced by:
    TABLE "number" CONSTRAINT "number_contact_id_fkey" FOREIGN KEY (contact_id) REFERENCES contact(contact_id)

contact_application=# \d number;
                        Table "public.number"
   Column   |         Type          | Collation | Nullable | Default 
------------+-----------------------+-----------+----------+---------
 number_id  | character varying(40) |           | not null | 
 number     | bigint                |           | not null | 
 type       | integer               |           | not null | 
 contact_id | character varying(40) |           | not null | 
 user_id    | character varying(40) |           | not null | 
Indexes:
    "number_pkey" PRIMARY KEY, btree (number_id)
Foreign-key constraints:
    "number_contact_id_fkey" FOREIGN KEY (contact_id) REFERENCES contact(contact_id)
    "number_user_id_fkey" FOREIGN KEY (user_id) REFERENCES users(user_id)


*/