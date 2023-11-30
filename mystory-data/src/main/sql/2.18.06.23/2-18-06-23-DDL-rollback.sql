--2-18-06-23-DDL-rollback.sql

SET search_path TO mystory;


-- f_advert_attr
ALTER TABLE f_advert_attr drop CONSTRAINT f_advert_attr_uq;

-- f_advert_watch
ALTER TABLE f_advert_watch DROP COLUMN user_oauth2_id;

-- a_page_watch
DROP TABLE a_page_watch;