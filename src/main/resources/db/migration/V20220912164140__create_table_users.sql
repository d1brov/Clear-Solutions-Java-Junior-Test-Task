CREATE TABLE "users"
(
    "id"                SERIAL,
    "email"             VARCHAR(64) NOT NULL,
    "first_name"        VARCHAR(64) NOT NULL,
    "last_name"         VARCHAR(64) NOT NULL,
    "birth_date"        DATE NOT NULL,
    "address"           VARCHAR(128),
    "phone"             VARCHAR(16),
    PRIMARY KEY (id)
);