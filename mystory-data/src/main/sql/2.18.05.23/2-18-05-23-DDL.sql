--2-18-05-23-DDL.sql

SET search_path TO mystory;

-- s_user
ALTER TABLE s_user ADD COLUMN about_me VARCHAR(323);


-- s_user_oauth2
ALTER TABLE s_user_oauth2 ADD COLUMN about_me VARCHAR(323);


-- a_audit

-- see AuditType.java
-- default 1 = ENTITY_CRUD, 
ALTER TABLE a_audit ADD COLUMN updated_timestamp timestamp with time zone;
ALTER TABLE a_audit ADD COLUMN audit_type SMALLINT default 1;
ALTER TABLE a_audit ADD COLUMN user_uid VARCHAR(256);



CREATE TABLE IF NOT EXISTS s_user_watch (
	"id"						BIGSERIAL NOT NULL PRIMARY KEY,
	"user_id_watched"			BIGINT,
	"user_oauth2_id_watched"	BIGINT,
	"user_id_watcher"			BIGINT,
	"user_oauth2_id_watcher"	BIGINT,
	"watcher_ipaddress"			VARCHAR(64) NOT NULL,
	"view_date"					DATE DEFAULT CURRENT_DATE NOT NULL,
	"created_timestamp"			TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
	"updated_timestamp"			TIMESTAMP WITH TIME ZONE,
		
	CONSTRAINT fk_s_user_watch_s_user_user_id_watched FOREIGN KEY (user_id_watched) REFERENCES S_USER(id), 
	CONSTRAINT fk_s_user_watch_s_user_oauth2_user_oauth2_id_watched FOREIGN KEY (user_oauth2_id_watched) REFERENCES S_USER_OAUTH2(id),
	CONSTRAINT fk_s_user_watch_s_user_user_id_watcher FOREIGN KEY (user_id_watcher) REFERENCES S_USER(id),
	CONSTRAINT fk_s_user_watch_s_user_oauth2_user_oauth2_id_watcher FOREIGN KEY (user_oauth2_id_watcher) REFERENCES S_USER_OAUTH2(id)
   
);