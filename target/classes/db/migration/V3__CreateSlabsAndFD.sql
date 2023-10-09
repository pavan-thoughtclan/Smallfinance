CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE slabs (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY ,
    tenures VARCHAR(255) NOT NULL,
    interest_rate VARCHAR(255),
    type_of_transaction VARCHAR(255)
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
    pre_mature_withdrawal DATE DEFAULT NULL,
    total_amount DOUBLE PRECISION,
    interest_amount DOUBLE PRECISION DEFAULT 0.0,
    FOREIGN KEY (account_number) REFERENCES account_details(account_number),
    FOREIGN KEY (slab_id) REFERENCES slabs(id)
);


