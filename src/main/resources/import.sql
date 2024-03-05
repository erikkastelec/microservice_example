INSERT INTO user (id, id1, id2, id3) VALUES (1, 'ID1', 'ID2', 'ID3');
INSERT INTO address (id, user_id, title, first_name, last_name, street, house_number, postal_code, post_office_name, city, country, is_default) VALUES
(1, 1, 'Home', 'John', 'Doe', '123 Main St', '1', '12345', 'Main Post Office', 'City', 'Slovenia', true),
(2, 1, 'Work', 'John', 'Doe', '456 Work Ave', '2', '67890', 'Work Post Office', 'WorkCity', 'Slovenia', false);
