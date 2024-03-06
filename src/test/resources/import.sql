-- Insert test users
INSERT INTO app_user (id, id1, id2, id3) VALUES 
(1, 'User-001', 'ID-1', 'ID-A'),
(2, 'User-002', 'ID-2', 'ID-B');

-- Revised import.sql without manually setting IDs
INSERT INTO address (user_id, title, first_name, last_name, street, house_number, postal_code, post_office_name, city, country, is_default) VALUES
(1, 'Home', 'John', 'Doe', '123 Main St', '1', '1000', 'Main Post Office', 'CityName', 'Slovenia', true),
(1, 'Work', 'John', 'Doe', '456 Work Ave', '2', '2000', 'Work Post Office', 'CityName', 'Slovenia', false),
(2, 'Home', 'Jane', 'Doe', '789 Second St', '10', '3000', 'Second Post Office', 'AnotherCity', 'Slovenia', true);
