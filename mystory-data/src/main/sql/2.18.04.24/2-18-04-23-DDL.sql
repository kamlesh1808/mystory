--2-18-04-23-DDL.sql

SET search_path TO mystory;


-- s_user
ALTER TABLE s_user ADD COLUMN uid BIGINT;

/* 
9116074925771705344
9136385126863555584
9124209595284434944
9150230284137194496
9109064557941562368
9109950573201742848
9113694933514001408
9154641512130835456
9173423368506681344


 id  |   username   | uid
-----+--------------+-----
 100 | healed4life  |   8
 101 | mystoryadmin |   6
 102 | sysusercomm  |   4
 103 | sysguestuser |   1
 104 | Sankalp99    |   2
 105 | user1876     |   3
 106 | bitsnbytes18 |   5
 107 | lifeloved12  |   7
 
*/

UPDATE s_user SET uid = 9116074925771705344 WHERE id = 100;
UPDATE s_user SET uid = 9136385126863555584 WHERE id = 101;
UPDATE s_user SET uid = 9124209595284434944 WHERE id = 102;
UPDATE s_user SET uid = 9150230284137194496 WHERE id = 103;
UPDATE s_user SET uid = 9109064557941562368 WHERE id = 104;
UPDATE s_user SET uid = 9109950573201742848 WHERE id = 105;
UPDATE s_user SET uid = 9154641512130835456 WHERE id = 106;
UPDATE s_user SET uid = 9173423368506681344 WHERE id = 107;
UPDATE s_user SET uid = 9183423368506681323 WHERE id = 108;




ALTER TABLE s_user ALTER COLUMN uid SET NOT NULL;

ALTER TABLE a_story ALTER COLUMN user_id DROP NOT NULL;
ALTER TABLE a_story ADD COLUMN user_oauth2_id BIGINT;
ALTER TABLE a_story ADD CONSTRAINT fk_a_story_s_user_oauth2 FOREIGN KEY (user_oauth2_id) REFERENCES S_USER_OAUTH2;

ALTER TABLE s_user_comm ALTER COLUMN user_id DROP NOT NULL;
ALTER TABLE s_user_comm ADD COLUMN user_oauth2_id BIGINT;
ALTER TABLE s_user_comm ADD CONSTRAINT fk_s_user_comm_s_user_oauth2 FOREIGN KEY (user_oauth2_id) REFERENCES S_USER_OAUTH2;

ALTER TABLE a_story_watch ADD COLUMN user_oauth2_id BIGINT;
ALTER TABLE a_story_watch ADD CONSTRAINT fk_a_story_watch_s_user_oauth2 FOREIGN KEY (user_oauth2_id) REFERENCES S_USER_OAUTH2;