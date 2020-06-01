CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE USERS (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    birthday DATE,
    date_created TIMESTAMPTZ DEFAULT NOW(),
    last_updated TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE USER_AUTHORITIES (
    id uuid default uuid_generate_v4() PRIMARY KEY,
    user_id uuid NOT NULL REFERENCES users(id),
    authority varchar(50) NOT NULL,
    UNIQUE (user_id, authority)
);

alter table users alter column enabled set default true;

