--liquibase formatted sql

--changeset arsudakov:1
CREATE TABLE IF NOT EXISTS person (
     id SERIAL PRIMARY KEY NOT NULL,
     login VARCHAR(2000),
     password VARCHAR(2000)
);