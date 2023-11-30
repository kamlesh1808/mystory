
---DDL start
SET search_path TO mystory;

DROP TABLE a_story_watch;
DROP TABLE a_story_reply;
DROP TABLE a_story_tag;
DROP TABLE a_tag;
DROP TABLE a_story;
DROP TABLE s_user;
DROP TABLE a_address;
DROP TABLE a_contactus;
DROP TABLE t_country;

CREATE TABLE IF NOT EXISTS t_country (
    "alpha2_code" CHAR(2) NOT NULL PRIMARY KEY,
		"name_en" VARCHAR(128) NOT NULL UNIQUE,
		"alpha3_code" CHAR(3) UNIQUE,
		"numeric_code" SMALLINT UNIQUE,
		"dialing_code" VARCHAR(32),
		"local_name" VARCHAR(50) UNIQUE,
		"status" SMALLINT,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone
);

CREATE TABLE IF NOT EXISTS a_contactus (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
		"first_name" VARCHAR(20) NOT NULL,
		"last_name" VARCHAR(20) NOT NULL,
		"username" VARCHAR(20),
		"email" VARCHAR(64) NOT NULL,
    "country" CHAR(2) NOT NULL,
    "contactus_text" VARCHAR(10000) NOT NULL,
    "status" SMALLINT NOT NULL,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,
      
    CONSTRAINT fk_a_contactus_t_country FOREIGN KEY (country) REFERENCES T_COUNTRY(alpha2_code)
    
  );
  
CREATE TABLE IF NOT EXISTS a_address (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
		"line1" VARCHAR(50),
		"line2" VARCHAR(50),
		"city" VARCHAR(50),
		"state_prov" VARCHAR(50),
		"country" CHAR(2),
		"postal_code_zip" VARCHAR(50),
		"type" SMALLINT,
		"status" SMALLINT,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,

    CONSTRAINT fk_a_address_t_country FOREIGN KEY (country) REFERENCES T_COUNTRY(alpha2_code) 
	
  );

CREATE TABLE IF NOT EXISTS s_user (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
		"first_name" VARCHAR(20) NOT NULL,
		"last_name" VARCHAR(20) NOT NULL,
		"username" VARCHAR(20),
		"email" VARCHAR(64),
		"salt" VARCHAR(48) NOT NULL,
		"hash" VARCHAR(48) NOT NULL,
		"status" SMALLINT NOT NULL,
		"email_verify_token" varchar(20),
		"invalid_signin_attempts" SMALLINT,
		"user_role_type" SMALLINT NOT NULL,
    "address_id" BIGINT NOT NULL,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,
		
		CONSTRAINT uc_username UNIQUE (username),
		CONSTRAINT uc_email UNIQUE (email),
    CONSTRAINT fk_s_user_a_address FOREIGN KEY (address_id) REFERENCES A_ADDRESS(id)

  );

CREATE TABLE IF NOT EXISTS a_story (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
		"user_id" BIGINT NOT NULL,
		"story_name" BIGINT NOT NULL,
		"story_type" BIGINT NOT NULL,
		"access_type" BIGINT NOT NULL,
		"title" VARCHAR(128),
		"story_text" VARCHAR(100000),
		"status" SMALLINT NOT NULL,
    "views" SMALLINT NOT NULL default 0,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,
		
		CONSTRAINT fk_a_story_s_user FOREIGN KEY (user_id) REFERENCES S_USER(id) 
	);
	
CREATE TABLE IF NOT EXISTS a_tag (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
		"name" VARCHAR(32) UNIQUE NOT NULL,
		"description" VARCHAR(32),
		"status" SMALLINT NOT NULL,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone
	);

	
CREATE TABLE IF NOT EXISTS a_story_tag (
    "a_story_id" BIGSERIAL NOT NULL,
		"a_tag_id" BIGINT NOT NULL,		
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,
		
    CONSTRAINT pk_a_story_tag_id PRIMARY KEY (a_tag_id, a_story_id),
		CONSTRAINT fk_a_story_tag_l_story_tag_type FOREIGN KEY (a_tag_id) REFERENCES a_tag(id), 
		CONSTRAINT fk_a_story_tag_a_story FOREIGN KEY (a_story_id) REFERENCES a_story(id) 

	);


CREATE TABLE IF NOT EXISTS a_story_reply (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
		"reply" VARCHAR(10000) NOT NULL,
    "story_id" BIGINT,
    "user_id" BIGINT,
    "parent_id" BIGINT NULL,
    "reply_num" SMALLINT NOT NULL default 0,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,
		
    CONSTRAINT fk_a_story_reply_story_id FOREIGN KEY (story_id) REFERENCES A_STORY(id), 
    CONSTRAINT fk_a_story_reply_s_user FOREIGN KEY (user_id) REFERENCES S_USER(id), 
    CONSTRAINT fk_a_story_reply_a_story_reply FOREIGN KEY (parent_id) REFERENCES a_story_reply(id)  
  
  );
  
  
CREATE TABLE IF NOT EXISTS a_story_watch (
		"id" BIGSERIAL NOT NULL PRIMARY KEY,
    "story_id" BIGINT NOT NULL,
    "user_id" BIGINT NULL,
    "client_ipaddress" VARCHAR(64) NOT NULL,
		"view_date" date default current_date NOT NULL,
		"created_timestamp" timestamp with time zone default CURRENT_TIMESTAMP NOT NULL,
		"updated_timestamp" timestamp with time zone,
		
    CONSTRAINT fk_a_story_view_story_id FOREIGN KEY (story_id) REFERENCES A_STORY(id), 
    CONSTRAINT fk_a_story_view_s_user FOREIGN KEY (user_id) REFERENCES S_USER(id)
   
  );
  

---DDL end
		
		
--DML

INSERT INTO a_tag("name", "description", "status")
VALUES ('Introduction', 'Introduction Story', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Anxiety', 'Anxiety', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Pain', 'Pain', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Medications', 'Medications', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Stress', 'Stress', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Support', 'Support', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Treatment', 'Treatment', 1);


INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Canada', 'CA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('India', 'IN', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Brazil', 'BR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('China', 'CN', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Afghanistan', 'AF', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('United States of America', 'US', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Germany', 'DE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Australia', 'AU', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('France', 'FR', 3);


INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Antigua and Barbuda', 'AG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Argentina', 'AR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Armenia', 'AM', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Aruba', 'AW', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Austria', 'AT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Bahamas', 'BS', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Bangladesh', 'BD', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Barbados', 'BB', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Belgium', 'BE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Bulgaria', 'BG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Cambodia', 'KH', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Chad', 'TD', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Chile', 'CL', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Colombia', 'CO', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Costa Rica', 'CR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Croatia', 'HR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Cuba', 'CU', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Czech Republic', 'CZ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Denmark', 'DK', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Ecuador', 'EC', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Egypt', 'EG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('El Salvador', 'SV', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Estonia', 'EE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Ethiopia', 'ET', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Fiji', 'FJ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Finland', 'FI', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Ghana', 'GH', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Greece', 'GR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Greenland', 'GL', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Guatemala', 'GT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Guyana', 'GY', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Haiti', 'HT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Hong Kong', 'HK', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Hungry', 'HU', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Iceland', 'IS', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Indonesia', 'ID', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Iran', 'IR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Iraq', 'IQ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Ireland', 'IE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Israel', 'IL', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Italy', 'IT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Jamaica', 'JM', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Japan', 'JP', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Kazakhstan', 'KZ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Korea', 'KP', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Kyrgyzstan', 'KG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Latvia', 'LV', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Lebanon', 'LB', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Liberia', 'LR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Libya', 'LY', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Lithuania', 'LT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Luxembourg', 'LU', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Malaysia', 'MY', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Mexico', 'MX', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Mongolia', 'MN', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Morocco', 'MA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Myanmar', 'MM', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Namibia', 'NA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Nepal', 'NP', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Netherlands', 'NL', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('New Zealand', 'NZ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Mozambique', 'MZ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Nigeria', 'NG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Norway', 'NO', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Oman', 'OM', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Pakistan', 'PK', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Panama', 'PA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Papua New Guinea ', 'PG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Paraguay', 'PY', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Philippines', 'PH', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Poland', 'PL', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Portugal', 'PT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Puerto Rico', 'PR', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Qatar', 'QA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Romania', 'RO', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Russia', 'RU', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Saudi Arabia', 'SA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Senegal', 'SN', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Serbia', 'RS', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Singapore', 'SG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Slovakia', 'SK', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Somalia', 'SO', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('South Africa', 'ZA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('South Sudan', 'SS', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Spain', 'ES', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Sri Lanka', 'LK', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Sudan', 'SD', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Sweden', 'SE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Switzerland', 'CH', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Taiwan', 'TW', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Thailand', 'TH', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Trinidad and Tobago', 'TT', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Tunisia', 'TN', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Uganda', 'UG', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Ukraine', 'UA', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('United Arab Emirates', 'AE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Uruguay', 'UY', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Uzbekistan', 'UZ', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Venezuela', 'VE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Viet Nam', 'VN', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Yemen', 'YE', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Zambia', 'ZM', 3);
INSERT INTO t_country(name_en, alpha2_code, status) VALUES ('Zimbabwe', 'ZW', 3);


ALTER SEQUENCE s_user_id_seq restart with 100;
ALTER SEQUENCE a_story_id_seq restart with 100;
