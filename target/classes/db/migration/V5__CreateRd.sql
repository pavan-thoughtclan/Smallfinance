CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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