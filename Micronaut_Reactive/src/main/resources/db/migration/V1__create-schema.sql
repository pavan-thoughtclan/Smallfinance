CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    dob DATE,
    email VARCHAR(255),
    age INTEGER,
    aadhar_card_number VARCHAR(255),
    pan_card_number VARCHAR(255),
    phone_number VARCHAR(255),
    role_name VARCHAR(255) DEFAULT 'CUSTOMER'
);


CREATE TABLE account_details (
    account_number BIGINT PRIMARY KEY,
    account_type VARCHAR(255) DEFAULT 'Savings',
    opening_date DATE,
    closing_date DATE,
    balance DOUBLE PRECISION DEFAULT 0.0,
    kyc BOOLEAN DEFAULT FALSE,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE slabs (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY ,
    tenures VARCHAR(255) NOT NULL,
    interest_rate VARCHAR(255),
    type_of_transaction VARCHAR(255)
);

CREATE TABLE transactions (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    from_account_number BIGINT,
    to_account_number BIGINT,
    timestamp TIMESTAMP,
    which_transaction VARCHAR(255),
    description VARCHAR(255) DEFAULT NULL,
    balance DOUBLE PRECISION,
    FOREIGN KEY (from_account_number) REFERENCES account_details(account_number),
    FOREIGN KEY (to_account_number) REFERENCES account_details(account_number)
);


CREATE TABLE fixed_deposit (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY ,
    account_number BIGINT,
    slab_id UUID,
    interest_rate VARCHAR(255),
    amount DOUBLE PRECISION,
    deposited_date DATE,
    maturity_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    pre_mature_withdrawal DATE,
    total_amount DOUBLE PRECISION,
    interest_amount DOUBLE PRECISION DEFAULT 0.0,
    FOREIGN KEY (account_number) REFERENCES account_details(account_number),
    FOREIGN KEY (slab_id) REFERENCES slabs(id)
);

CREATE TABLE loan (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    applied_date DATE,
    start_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    status VARCHAR(255) DEFAULT 'UNDER_REVIEW',
    loaned_amount DOUBLE PRECISION,
    slab_id UUID,
    account_number BIGINT,
    type_of_loan VARCHAR(255),
    loan_end_date DATE,
    interest VARCHAR(255),
    interest_amount DOUBLE PRECISION,
    total_amount DOUBLE PRECISION,
    remaining_amount DOUBLE PRECISION,
    monthly_interest_amount INT,
    next_payment_date DATE,
    missed_payment_count INT DEFAULT 0,
    total_missed_payments INT DEFAULT 0,
    FOREIGN KEY (slab_id) REFERENCES slabs(id),
    FOREIGN KEY (account_number) REFERENCES account_details(account_number)
);


CREATE TABLE repayment (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    loan_id UUID,
    month_number INT,
    pay_amount DOUBLE PRECISION,
    payment_status VARCHAR(255) DEFAULT 'UPCOMING',
    transaction_id UUID,
    penalty BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (loan_id) REFERENCES loan(id)
);

CREATE TABLE recurring_deposit (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    account_number BIGINT,
    month_tenure INT,
    monthly_paid_amount DOUBLE PRECISION,
    maturity_amount DOUBLE PRECISION,
    start_date DATE,
    maturity_date DATE,
    status VARCHAR(255) DEFAULT 'ACTIVE',
    interest VARCHAR(255),
    next_payment_date DATE,
    total_missed_payment_count INT DEFAULT 0,
    missed_payments INT DEFAULT 0,
    FOREIGN KEY (account_number) REFERENCES account_details (account_number)
);

CREATE TABLE recurring_deposit_payment (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    recurring_deposit_id UUID,
    month_number INT,
    pay_amount DOUBLE PRECISION,
    payment_status VARCHAR(255) DEFAULT 'UPCOMING',
    transaction_id UUID,
    FOREIGN KEY (recurring_deposit_id) REFERENCES recurring_deposit (id)
);















