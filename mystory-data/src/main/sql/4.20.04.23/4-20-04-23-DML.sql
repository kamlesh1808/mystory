--4-20-05-23-DML.sql

SET search_path TO mystory;

select * from a_tag;

INSERT INTO a_tag("name", "description", "status")
VALUES ('COVID-19', 'COVID-19', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Disease', 'Disease', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Health', 'Health', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Family', 'Family', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Google Cloud Platform', 'Google Cloud Platform', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Libre Office', 'Libre Office', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('pdf', 'pdf', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Virus', 'Virus', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Vaccine', 'Vaccine', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Windows 10', 'Windows 10', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Learn', 'Learn', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Grow', 'Grow', 1);

update a_tag set name = 'COVID-19', description='COVID-19' where name = 'COVID19';

commit;
select * from a_tag;