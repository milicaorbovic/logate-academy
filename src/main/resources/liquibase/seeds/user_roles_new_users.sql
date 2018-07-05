INSERT INTO user_roles(user_id, role_id) 
VALUES 
(
	(SELECT id FROM users WHERE first_name = 'Milica'), 
	(SELECT id FROM roles WHERE name = 'ROLE_USER')
);