-- story tags cleanup 2018-12-16

SET search_path TO mystory;


delete from a_story_watch where story_id in (204, 205, 206, 207, 208, 209);
delete from f_advert_watch where story_id in (204, 205, 206, 207, 208, 209);
delete from a_story where id in (204, 205, 206, 207, 208, 209);
commit;
--delete from a_story_watch where story_id in (191, 178, 165, 164, 174 , 202, 201, 188, 173, 158, 192, 179, 167, 152, 194, 181, 169, 163, 193, 180, 168,159, 196, 183, 170,156,  200, 187, 177, 157, 195, 182, 166, 162, 199, 186, 175, 160, 189, 161, 197, 184, 171, 154, 203, 190, 176, 153, 198, 185, 172, 155);
--delete from f_advert_watch where story_id in (191, 178, 165, 164, 174 , 202, 201, 188, 173, 158, 192, 179, 167, 152, 194, 181, 169, 163, 193, 180, 168,159, 196, 183, 170,156,  200, 187, 177, 157, 195, 182, 166, 162, 199, 186, 175, 160, 189, 161, 197, 184, 171, 154, 203, 190, 176, 153, 198, 185, 172, 155);
--delete from a_story where id in (191, 178, 165, 164, 174 , 202, 201, 188, 173, 158, 192, 179, 167, 152, 194, 181, 169, 163, 193, 180, 168,159, 196, 183, 170,156,  200, 187, 177, 157, 195, 182, 166, 162, 199, 186, 175, 160, 189, 161, 197, 184, 171, 154, 203, 190, 176, 153, 198, 185, 172, 155);
--commit;
