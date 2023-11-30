--2-18-03-23-DDL.sql

SET search_path TO mystory;


-- s_user
ALTER TABLE s_user ALTER COLUMN username SET NOT NULL;


-- s_user_oauth2
CREATE TABLE IF NOT EXISTS s_user_oauth2 (
	"id" BIGSERIAL CONSTRAINT pk_s_user_oauth2 PRIMARY KEY,
	"user_name" VARCHAR(64),
	"user_role_type" SMALLINT NOT NULL,
	"status" SMALLINT NOT NULL,
	"id_oauth2" VARCHAR(256) NOT NULL,
	"first_name" VARCHAR(32) NOT NULL,
	"last_name" VARCHAR(32) NOT NULL,	
	"email" VARCHAR(254),
	"link" VARCHAR(256),
	"locale" VARCHAR(8),
	"picture_url" VARCHAR(256),
	"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
	"updated_timestamp" timestamp with time zone,	
	
	CONSTRAINT uk_id_oauth2_user_role_type UNIQUE (id_oauth2, user_role_type) 
);