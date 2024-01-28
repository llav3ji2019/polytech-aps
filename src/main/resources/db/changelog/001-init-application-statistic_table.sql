--liquibase formatted sql

--changeset author:llav3ji2019
create table application (
    id serial primary key,
    probability_of_failure numeric,
    system_request_time_average numeric
);
