CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    id1 VARCHAR(255) UNIQUE,
    id2 VARCHAR(255) UNIQUE,
    id3 VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS addresses (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title VARCHAR(255),
    institution_name VARCHAR(255),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house_number VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    post_office_name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) CHECK (country = 'Slovenia') NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
