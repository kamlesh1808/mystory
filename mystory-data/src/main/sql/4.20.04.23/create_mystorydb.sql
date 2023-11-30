--create_mystorydb.sql
CREATE ROLE mystoryadmin  WITH LOGIN PASSWORD 'adminadmin' CREATEDB CREATEROLE;

SET ROLE mystoryadmin;

CREATE database mystorydb;
\connect mystorydb;

CREATE SCHEMA mystory;

SET search_path TO mystory;

GRANT USAGE, CREATE ON SCHEMA mystory to mystoryadmin;
GRANT ALL privileges ON ALL tables in schema mystory to mystoryadmin;


GRANT all privileges ON ALL sequences in schema mystory to mystoryadmin;

ALTER DEFAULT PRIVILEGES IN SCHEMA mystory GRANT UPDATE, INSERT, SELECT, DELETE ON TABLES TO mystoryadmin;

ALTER DEFAULT PRIVILEGES IN SCHEMA mystory GRANT UPDATE, SELECT ON sequences TO mystoryadmin;