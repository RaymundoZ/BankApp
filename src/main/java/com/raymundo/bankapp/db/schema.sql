CREATE TABLE IF NOT EXISTS CLIENT (
uuid binary(255) primary key,
name varchar(15) not null,
surname varchar(15) not null,
patronymic varchar(15) not null,
phone_number varchar(30) not null,
email varchar(30) not null,
passport_number varchar(30) not null
);
CREATE TABLE IF NOT EXISTS CREDIT (
uuid binary(255) primary key,
credit_limit float not null check(credit_limit > 0),
interest_rate float not null check(interest_rate > 0)
);
CREATE TABLE IF NOT EXISTS PAYMENT_SCHEDULE (
uuid binary(255) primary key,
payment_date timestamp not null,
payment_amount float not null check(payment_amount > 0),
loan_repayment_amount float not null check(loan_repayment_amount > 0),
interest_payment_amount float not null check(interest_payment_amount > 0)
);
CREATE TABLE IF NOT EXISTS LOAN_OFFER (
uuid binary(255) primary key,
client_uuid binary(255) not null references CLIENT(uuid) on delete cascade,
credit_uuid binary(255) not null references CREDIT(uuid) on delete cascade,
credit_amount float not null check(credit_amount > 0),
);
CREATE TABLE IF NOT EXISTS LOAN_PAYMENT_SCHEDULE(
loan_offer_uuid binary(255) not null references LOAN_OFFER(uuid) on delete cascade,
payment_schedule_uuid binary(255) not null references PAYMENT_SCHEDULE(uuid) on delete cascade
);
CREATE TABLE IF NOT EXISTS BANK (
uuid binary(255) primary key
);
CREATE TABLE IF NOT EXISTS BANK_CLIENT (
bank_uuid binary(255) not null references BANK(uuid) on delete cascade,
client_uuid binary(255) not null references CLIENT(uuid) on delete cascade
);
CREATE TABLE IF NOT EXISTS BANK_CREDIT (
bank_uuid binary(255) not null references BANK(uuid) on delete cascade,
credit_uuid binary(255) not null references CREDIT(uuid) on delete cascade
);


