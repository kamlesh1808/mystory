--3-18-10-23-DML.sql

SET search_path TO mystory;



INSERT INTO a_tag("name", "description", "status")
VALUES ('Advertisement', 'Advertisement', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Amazon', 'Amazon', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Architecture', 'Architecture', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Article', 'Article', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Book', 'Book', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('DevOps', 'DevOps', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Git SCM', 'Git SCM', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Google', 'Google', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Jakarta EE', 'Jakarta EE', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Microservices', 'Microservices', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Money', 'Money', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Monetization', 'Monetization', 1); 

INSERT INTO a_tag("name", "description", "status")
VALUES ('PostgreSQL', 'PostgreSQL', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Product', 'Product', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Website', 'Website', 1);

INSERT INTO a_tag("name", "description", "status")
VALUES ('Wildfly AS', 'Wildfly Application Server', 1);


-- Advert Book
-- Introduction to Programming using Java

INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
	VALUES (DEFAULT, 'Introduction to Programming using Java', 'Introduction to Programming using Java', 1, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
    VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Free Book - Recommended Reading', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
    VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'Introduction to Programming using Java', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
    VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'David J. Eck', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
    VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'http://math.hws.edu/javanotes/', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
    VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'http://math.hws.edu/javanotes/javanotes7-cover-180x235.png', CURRENT_TIMESTAMP);
    
INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
    VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);


-- Advert Book
-- Programming in D

INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'Programming in D', 'Programming in D', 1, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Free Book - Recommended Reading', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'Programming in D', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'A good book for learning programming in general', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'http://ddili.org/ders/d.en/', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'http://ddili.org/ders/d.en/cover_thumb.png', CURRENT_TIMESTAMP);
    
INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);


-- Advert Book
-- Java 8 in Action

INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'Java 8 in Action', 'Java 8 in Action', 1, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement - A MUST Read', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'Java 8 in Action', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'A MUST read for Java 8 learning', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2E5Afvl', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/51JNZZSTmFL._SX397_BO1,204,203,200_.jpg', CURRENT_TIMESTAMP);
    
INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);


-- Advert Book
-- Spring in Action 5th Edition

INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'Spring in Action 5th Edition', 'Spring in Action 5th Edition', 1, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'Spring in Action', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), '5th Edition', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2RuUHbX', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/51KYE-eYRvL._SX397_BO1,204,203,200_.jpg', CURRENT_TIMESTAMP);
    
INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);


-- Advert Product
-- Doctor's Best Stabilized R-Lipoic Acid
INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'Doctor''s Best Stabilized R-Lipoic Acid', 'Doctor''s Best Stabilized R-Lipoic Acid', 2, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'Doctor''s Best Stabilized R-Lipoic Acid', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'with BioEnhanced Na-RALA, Non-GMO, Gluten Free, Vegan, Helps Maintain Blood Sugar Levels, 100 mg 60 Veggie Caps', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2zpCpC0', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/71j81ZgdqPL._SY679_.jpg', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);


-- Advert Product
-- ACDelco AAA Batteries, Alkaline Battery, Bulk Pack, 100 Count
INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'ACDelco AAA Batteries', 'Alkaline Battery, Bulk Pack, 100 Count', 2, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'ACDelco AAA Batteries', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'Alkaline Battery, Bulk Pack, 100 Count', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2C38eSP', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/71jMa3mjZuL._SX522_.jpg', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);


-- Advert Product
-- Pampers Easy Ups Training Pants Pull On Disposable Diapers
INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'Pampers Easy Ups Training Pants Pull On Disposable Diapers', 'for Girls Size 4 (2T-3T), 164 Count, ONE MONTH SUPPLY', 2, 300, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'Pampers Easy Ups Training Pants Pull On Disposable Diapers', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'for Girls Size 4 (2T-3T), 164 Count, ONE MONTH SUPPLY', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2pFxOWi', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/91Uml5zZq4L._SX522_.jpg', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);



-- Advert Product
-- All-new Echo Plus (2nd Gen)
INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'All-new Echo Plus (2nd Gen)', 'Premium sound with built-in smart home hub - Heather Gray', 2, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'All-new Echo Plus (2nd Gen)', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'Premium sound with built-in smart home hub - Heather Gray', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2pFmz0f', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/G/01/kindle/dp/2018/100495/l_CC._CB483408332_.png', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '2', CURRENT_TIMESTAMP);



-- Advert Product
-- OnePlus 6 A6003 Dual-SIM
INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'OnePlus 6 A6003 Dual-SIM', '(128GB Storage | 8GB RAM) Factory Unlocked 4G Smartphone (Midnight Black) - International Version', 2, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'OnePlus 6 A6003 Dual-SIM', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), '(128GB Storage | 8GB RAM) Factory Unlocked 4G Smartphone (Midnight Black) - International Version', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2RC6Hsi', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/71b0GYfMVsL._SL1500_.jpg', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '8', CURRENT_TIMESTAMP);




-- Advert Product
-- ASUS ZenFone 5Q (ZC600KL-S630-4G-64G-BK) - 6”
INSERT INTO f_advert(id, name, description, advert_type, advert_priority_type, status, start_timestamp, end_timestamp,  created_timestamp, updated_timestamp)
VALUES (DEFAULT, 'ASUS ZenFone 5Q (ZC600KL-S630-4G-64G-BK) - 6”', 'FHD 2160x1080 display - Quad-camera - 4GB RAM - 64GB storage - LTE Unlocked Dual SIM Cell Phone - US Warranty - Black', 2, 500, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '123 day', CURRENT_TIMESTAMP, null);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (1, currval(pg_get_serial_sequence('f_advert', 'id')), 'Amazon Advertisement', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (2, currval(pg_get_serial_sequence('f_advert', 'id')), 'ASUS ZenFone 5Q (ZC600KL-S630-4G-64G-BK) - 6”', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (3, currval(pg_get_serial_sequence('f_advert', 'id')), 'FHD 2160x1080 display - Quad-camera - 4GB RAM - 64GB storage - LTE Unlocked Dual SIM Cell Phone - US Warranty - Black', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (4, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://amzn.to/2RxeFmt', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (5, currval(pg_get_serial_sequence('f_advert', 'id')), 'https://images-na.ssl-images-amazon.com/images/I/81dMDldt8oL._AC_UL115_.jpg', CURRENT_TIMESTAMP);

INSERT INTO f_advert_attr(attr_id, f_advert_id, value, created_timestamp)
VALUES (6, currval(pg_get_serial_sequence('f_advert', 'id')), '8', CURRENT_TIMESTAMP);