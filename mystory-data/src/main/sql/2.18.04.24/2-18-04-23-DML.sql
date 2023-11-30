--2-18-04-23-DML.sql

SET search_path TO mystory;


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

INSERT INTO a_tag("name", "description", "status")
VALUES ('Absolute Zero', 'Absolute Zero', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('All is Well', 'All is Well', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Anonymity', 'Anonymity', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Complicated', 'Complicated', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Dark Matter', 'Dark Matter', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Galaxy', 'Galaxy', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Prayer', 'Prayer', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Security', 'Security', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Sleep', 'Sleep', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Digital Ocean', 'Digital Ocean', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Java', 'Java', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Java EE', 'Java EE', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Linux', 'Linux', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Software Engineering', 'Software Engineering', 1);