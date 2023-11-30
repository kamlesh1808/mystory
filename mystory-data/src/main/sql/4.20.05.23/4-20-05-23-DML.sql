--4-20-05-23-DML.sql

SET search_path TO mystory;

select * from s_user;

-- clean up A_CONTACTUS
select * from A_CONTACTUS;

delete from A_CONTACTUS where status = 1
commit;


-- delete from s_user 105	user1876
select *  from s_user_comm  where user_id = 105
delete from s_user_comm_attr where user_comm_id in (11,12)
delete from s_user_comm  where user_id = 105
delete from s_user where id = 105
commit;

-- delete from s_user 108	Test180323A1
delete from s_user_comm_attr where user_comm_id in (28)
select *  from s_user_comm  where user_id = 108
delete from s_user_comm  where user_id = 108
delete from s_user where id = 108
commit;


-- delete san 104
select * from s_user_comm  where user_id = 104
delete from s_user_comm_attr  where user_comm_id in (8, 9);
delete from s_user_comm  where user_id = 104
delete from a_story_watch where user_id = 104
delete from s_user where id = 104
commit;