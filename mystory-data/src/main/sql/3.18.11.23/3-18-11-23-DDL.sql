--3-18-11-23-DML.sql

SET search_path TO mystory;

CREATE TABLE IF NOT EXISTS f_advert_story_name (
    "id" bigserial NOT NULL PRIMARY KEY,
    "f_advert_id" bigint NOT NULL,
    "story_name_id" bigint NOT NULL,   
    "status" smallint NOT NULL default 3,
    "created_timestamp" timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_timestamp" timestamp with time zone,
    
    CONSTRAINT fk_f_advert_story_name FOREIGN KEY (f_advert_id) REFERENCES f_advert(id)
);