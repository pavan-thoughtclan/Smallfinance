CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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