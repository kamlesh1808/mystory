--2-18-06-23-DDL.sql

SET search_path TO mystory;


-- f_advert_attr
ALTER TABLE f_advert_attr ADD CONSTRAINT f_advert_attr_uq UNIQUE (attr_id, f_advert_id);

-- f_advert_watch
ALTER TABLE f_advert_watch ALTER COLUMN user_id DROP NOT NULL;
ALTER TABLE f_advert_watch ADD COLUMN user_oauth2_id BIGINT;
ALTER TABLE f_advert_watch ADD CONSTRAINT fk_f_advert_watch_s_user_oauth2 FOREIGN KEY (user_oauth2_id) REFERENCES S_USER_OAUTH2;

ALTER TABLE f_advert_watch ADD COLUMN watcher_ipaddress VARCHAR(64) NOT NULL;

-- a_page_watch
CREATE TABLE a_page_watch (
    id bigserial NOT NULL PRIMARY KEY,
    page_id bigint NOT NULL,
    user_id_watcher bigint,
    user_oauth2_id_watcher bigint,
    view_date       DATE DEFAULT CURRENT_DATE NOT NULL,
    watcher_ipaddress CHARACTER VARYING(64) NOT NULL,
    created_timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_timestamp TIMESTAMP WITH TIME ZONE,
    
    CONSTRAINT fk_a_page_watch_s_user FOREIGN KEY (user_id_watcher) REFERENCES s_user (id),
    CONSTRAINT fk_a_page_watch_s_user_oauth2 FOREIGN KEY (user_oauth2_id_watcher) REFERENCES s_user_oauth2 (id)
);


-- a_black_list
CREATE TABLE a_black_list (
    id bigserial NOT NULL PRIMARY KEY,
    black_list_type_id bigint NOT NULL,
    black_list_value VARCHAR(64) NOT NULL,
    record_status SMALLINT,
    created_timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_timestamp TIMESTAMP WITH TIME ZONE
)





