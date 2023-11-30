SET search_path TO mystory;

-- Data correction made to production mystroydb
update mystory.s_user set email = 'healed.4life5@gmail.com' where username = 'healed4life';
update mystory.s_user set email = 'work.mystory@gmail.com' where username = 'mystoryadmin';