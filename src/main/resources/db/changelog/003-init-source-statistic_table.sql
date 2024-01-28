--liquibase formatted sql

--changeset author:llav3ji2019
create table source (
    id serial primary key,
    request_amount int,
    rejected_amount int,
    avg_requests_time numeric,
    common_requests_time numeric,
    probability_of_failure numeric
);
