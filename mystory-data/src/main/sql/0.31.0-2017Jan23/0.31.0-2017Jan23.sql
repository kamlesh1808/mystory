SET search_path TO mystory;

--drop table f_advert cascade;
--drop table f_advert_attr;
--drop table f_advert_watch;
--commit;

CREATE TABLE IF NOT EXISTS f_advert (
    "id" bigserial NOT NULL PRIMARY KEY,
    "name" character varying(128) NOT NULL UNIQUE,
    "description" character varying(128) NOT NULL,
    "advert_type" smallint NOT NULL,
    "advert_priority_type" smallint,
    "status" smallint NOT NULL,
    "start_timestamp" timestamp with time zone,
    "end_timestamp" timestamp with time zone,    
    "created_timestamp" timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_timestamp" timestamp with time zone
);

CREATE TABLE IF NOT EXISTS f_advert_attr (
    "id" bigserial NOT NULL PRIMARY KEY,
    "attr_id" bigint NOT NULL,
    "f_advert_id" bigint NOT NULL,
    "value" character varying(256) NOT NULL,
    "created_timestamp" timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_timestamp" timestamp with time zone,
    
    CONSTRAINT fk_f_advert_attr_f_advert FOREIGN KEY (f_advert_id) REFERENCES f_advert(id)
);

CREATE TABLE IF NOT EXISTS f_advert_watch (
    "id" bigserial NOT NULL PRIMARY KEY,
    "f_advert_id" bigint NOT NULL,
    "user_id" bigint NOT NULL,
    "story_id" bigint NOT NULL,    
    "advert_watch_type" smallint NOT NULL,
    "created_timestamp" timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_timestamp" timestamp with time zone,

    CONSTRAINT fk_f_advert_watch_f_advert FOREIGN KEY (f_advert_id) REFERENCES f_advert (id),
    CONSTRAINT fk_f_advert_watch_s_user FOREIGN KEY (user_id) REFERENCES S_USER (id),
    CONSTRAINT fk_f_advert_watch_a_story FOREIGN KEY (story_id) REFERENCES A_STORY (id)
);


grant all privileges on all tables in schema mystory to mystoryadmin;
grant all privileges on all sequences in schema mystory to mystoryadmin;
GRANT USAGE, CREATE ON SCHEMA mystory to mystoryadmin;

UPDATE mystory.s_user  SET user_role_type=5 WHERE username = 'sysguestuser';

commit;