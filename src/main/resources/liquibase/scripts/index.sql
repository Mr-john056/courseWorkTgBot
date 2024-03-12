-- liquibase formatted sql

-- changeset ekulakov:1
create table tasks(
    id bigserial primary key,
    chat_id bigint not null,
    message_text VARCHAR(255) not null,
    time_and_date TIMESTAMP not null
);