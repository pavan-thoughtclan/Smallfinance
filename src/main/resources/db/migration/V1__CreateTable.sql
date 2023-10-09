CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    dob DATE,
    email VARCHAR(255),
    age INT,
    aadhar_card_number VARCHAR(255),
    pan_card_number VARCHAR(255),
    phone_number VARCHAR(20),
    role_name VARCHAR(50) DEFAULT 'CUSTOMER'
);


CREATE TABLE account_details (
    account_number BIGINT PRIMARY KEY,
    account_type VARCHAR(255) DEFAULT 'Savings',
    opening_date DATE,
    closing_date DATE,
    balance DOUBLE PRECISION DEFAULT 0.0,
    kyc BOOLEAN DEFAULT FALSE,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

