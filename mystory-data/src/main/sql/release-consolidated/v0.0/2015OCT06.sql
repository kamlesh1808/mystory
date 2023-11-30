drop table a_audit cascade;
drop table a_audit_detail;

SET search_path TO mystory;


grant all privileges on all tables in schema mystory to mystoryadmin;
grant all privileges on all sequences in schema mystory to mystoryadmin;
GRANT USAGE, CREATE ON SCHEMA mystory to mystoryadmin;


ALTER DEFAULT PRIVILEGES IN SCHEMA mystory GRANT UPDATE, INSERT, SELECT, DELETE ON TABLES TO mystoryadmin;
ALTER DEFAULT PRIVILEGES IN SCHEMA mystory GRANT UPDATE, SELECT ON sequences TO mystoryadmin;

---DDL start
SET search_path TO mystory;

CREATE TABLE IF NOT EXISTS a_audit (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"entity_id" BIGINT NOT NULL,
  "a_entity_id" BIGINT NOT NULL,
	"event_type" SMALLINT NOT NULL,
	"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL
  );
  
   
CREATE TABLE IF NOT EXISTS a_audit_detail (
	"id" BIGSERIAL NOT NULL PRIMARY KEY,
	"a_audit_id" BIGINT NOT NULL,
	"attr_name" VARCHAR(64),
	"value" VARCHAR(1024),
		
	CONSTRAINT fk_a_audit_detail_a_audit FOREIGN KEY (a_audit_id) REFERENCES a_audit(id)
  );