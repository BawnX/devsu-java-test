Create SCHEMA IF NOT EXISTS clients;
Create SCHEMA IF NOT EXISTS accounts;
create table clients.person (
    id UUID not null,
    address varchar(300) not null,
    age integer not null,
    cellphone varchar(10) not null,
    created_at timestamp(6) not null,
    gender varchar(15) not null,
    name varchar(150) not null,
    updated_at timestamp(6) not null,
    primary key (id)
);
create table clients.client (
    id UUID not null,
    secret_key varchar(100) not null,
    secret_password varchar(50) not null,
    state boolean not null,
    person_id UUID,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    primary key (id),
    foreign key (person_id) references clients.person(id)
);
create table accounts.account (
    id UUID not null,
    balance float(34) not null,
    client_id UUID not null,
    created_at timestamp(6) not null,
    state boolean not null,
    type_account varchar(15) not null,
    updated_at timestamp(6) not null,
    primary key (id)
);
create table accounts.transaction (
    id UUID not null,
    amount float(34) not null,
    balance float(34) not null,
    transaction_date_time timestamp(6) not null,
    type_transaction varchar(15) not null,
    updated_at timestamp(6) not null,
    account_id UUID not null,
    primary key (id),
    FOREIGN KEY (account_id) REFERENCES accounts.account(id)
);