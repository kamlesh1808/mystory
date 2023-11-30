--2-18-05-23-DDL-rollback.sql

SET search_path TO mystory;


-- s_user
ALTER TABLE s_user DROP COLUMN about_me;


-- s_user_oauth2
ALTER TABLE s_user_oauth2 DROP COLUMN about_me;


-- a_audit
ALTER TABLE a_audit DROP COLUMN updated_timestamp;
ALTER TABLE a_audit DROP COLUMN audit_type;
ALTER TABLE a_audit DROP COLUMN user_uid;

DROP TABLE s_user_watch;