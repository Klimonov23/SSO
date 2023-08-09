--liquibase formatted sql

--changeset kklimonov:extensions-1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";