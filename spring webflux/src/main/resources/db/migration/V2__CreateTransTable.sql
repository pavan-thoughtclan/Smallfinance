CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Transactions (
    id UUID DEFAULT uuid_generate_v4()
      PRIMARY KEY,
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
