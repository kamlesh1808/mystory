--2-18-05-23-DML.sql

SET search_path TO mystory;

update a_tag set name = 'Gratitude', description='Gratitude' where id=13;

INSERT INTO a_tag("name", "description", "status")
VALUES ('Open Source', 'Open Source Software', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('AI', 'Artificial Intelligence', 1);


-- list s_user_oauth2 with user role type Google
select * from s_user_oauth2 where user_role_type = 6;

update s_user_oauth2 set user_name = 'oauth218Google' where user_name = 'oauth218@Google';
update s_user_oauth2 set user_name = 'work.mystoryGoogle' where user_name = 'work.mystory@Google';
