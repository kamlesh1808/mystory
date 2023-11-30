--2-18-03-23-DDL-rollback.sql

SET search_path TO mystory;


-- s_user
ALTER TABLE s_user ALTER COLUMN username DROP NOT NULL;


-- s_user_oauth2
DROP TABLE s_user_oauth2;