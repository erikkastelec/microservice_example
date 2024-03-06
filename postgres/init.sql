-- Create Users Table
CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    id1 VARCHAR(255),
    id2 VARCHAR(255),
    id3 VARCHAR(255)
);

-- Create Addresses Table
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(255),
    institution_name VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house_number VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    post_office_name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- Insert Users
INSERT INTO app_user (id1, id2, id3) VALUES 
('User-001', 'ID-1', 'ID-A'),
('User-002', 'ID-2', 'ID-B'),
('User-003', 'ID-3', 'ID-C');

-- Insert Addresses
-- Note: 'id' values are not included as they are auto-generated
INSERT INTO address (user_id, title, institution_name, first_name, last_name, street, house_number, postal_code, post_office_name, city, country, is_default) VALUES
(1, 'Home', NULL, 'John', 'Doe', '123 Main St', '1', '1000', 'Main Post Office', 'CityName', 'Slovenia', TRUE),
(1, 'Work', NULL, 'John', 'Doe', '456 Work Ave', '2', '2000', 'Work Post Office', 'CityName', 'Slovenia', FALSE),
(2, 'Home', NULL, 'Jane', 'Doe', '789 Second St', '10', '3000', 'Second Post Office', 'AnotherCity', 'Slovenia', TRUE);
