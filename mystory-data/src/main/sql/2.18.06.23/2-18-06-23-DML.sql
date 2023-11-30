--2-18-06-23-DML.sql

SET search_path TO mystory;


delete from f_advert_attr where f_advert_id in (1,2);
delete from f_advert_watch where f_advert_id in (1,2);
delete from f_advert where id in (1,2);


INSERT INTO a_black_list (black_list_type_id, black_list_value, record_status) values(6, '23.233.45.234', 3);
INSERT INTO a_black_list (black_list_type_id, black_list_value, record_status) values(6, '66.249.66.1', 3);

INSERT INTO a_black_list (black_list_type_id, black_list_value, record_status) values(8, '23.233.45.234', 3);
INSERT INTO a_black_list (black_list_type_id, black_list_value, record_status) values(8, '66.249.66.1', 3);

INSERT INTO a_black_list (black_list_type_id, black_list_value, record_status) values(10, '23.233.45.234', 3);
INSERT INTO a_black_list (black_list_type_id, black_list_value, record_status) values(10, '66.249.66.1', 3);
