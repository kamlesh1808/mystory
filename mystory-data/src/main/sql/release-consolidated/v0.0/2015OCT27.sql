---DDL start
SET search_path TO mystory;
CREATE TABLE IF NOT EXISTS s_user_comm (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"comm_name_id" SMALLINT NOT NULL,
	"status" SMALLINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
	"updated_timestamp" timestamp with time zone,
  
	CONSTRAINT fk_s_user_comm_s_user FOREIGN KEY (user_id) REFERENCES s_user(id)
  );
  
SET search_path TO mystory;  
CREATE TABLE IF NOT EXISTS s_user_comm_attr (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"attr_id" BIGINT NOT NULL,
	"value" VARCHAR(128) NOT NULL,
	"user_comm_id" BIGINT NOT NULL,
	"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
	"updated_timestamp" timestamp with time zone,
  
	CONSTRAINT fk_s_user_comm_attr_s_user_comm FOREIGN KEY (user_comm_id) REFERENCES s_user_comm(id)
  );