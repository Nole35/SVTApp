-- INSERT INTO users (username, password, email, registrationDate, firstName, lastName, roles, active)
-- VALUES ('nole', 'nole', 'nole@gmail.com', '2022-05-18', 'Novica', 'Lazic', 'ADMIN', 'true');
--
-- INSERT INTO users (username, password, email, registrationDate, firstName, lastName, roles, active)
-- VALUES ('jole', 'jole', 'jole@gmail.com', '2022-05-18', 'Jovica', 'Lazic', 'USER', 'true');
-- INSERT INTO users (username, password, email, registrationDate, firstName, lastName, roles, active)
-- VALUES ('krle', 'krle', 'krle@gmail.com', '2022-05-18', 'Krsto', 'Lazic', 'GROUPADMIN', 'true');
-- --
-- INSERT INTO post (content,  creation_date, image_path, `group_id`, user_id)
-- VALUES('objava objave 1', '2022-05-18', NULL, 1, 2);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 1', 1);



-- insert into reaction_post (timestamp, type, post_id, user_id) values ('2022-06-02', 1, 1, 2);

-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 2 community 1', 1);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 3 community 1', 1);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 4 community 1', 1);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 5 community 1', 1);
--
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 1 community 3', 3);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 2 community 3', 3);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 3 community 3', 3);
--
-- INSERT INTO rule (description, community_id)
--             VALUES('Rule number 4 community 3', 3);

-- INSERT INTO `group` (name, description, creation_date, user_id)
-- VALUES('Group1', 'Description of group1', '2022-05-18', 3);


Update users SET username='nole' where user_id = 1;
