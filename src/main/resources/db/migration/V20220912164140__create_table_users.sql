CREATE TABLE "users"
(
    "id"                SERIAL,
    "email"             VARCHAR(64) NOT NULL UNIQUE,
    "first_name"        VARCHAR(64) NOT NULL,
    "last_name"         VARCHAR(64) NOT NULL,
    "birth_date"        DATE NOT NULL,
    "address"           VARCHAR(128),       -- todo make separate table
    "phone"             VARCHAR(16) UNIQUE, -- todo set phone format
    PRIMARY KEY (id)
);