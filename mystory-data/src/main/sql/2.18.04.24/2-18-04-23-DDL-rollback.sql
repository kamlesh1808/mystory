--2-18-04-23-DDL-rollback.sql

SET search_path TO mystory;


-- s_user
ALTER TABLE s_user DROP COLUMN uid;


ALTER TABLE s_user DROP COLUMN uid;

ALTER TABLE a_story DROP COLUMN user_oauth2_id;

ALTER TABLE s_user_comm DROP COLUMN user_oauth2_id;

ALTER TABLE a_story_watch DROP COLUMN user_oauth2_id;