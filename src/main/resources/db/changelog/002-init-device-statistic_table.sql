--liquibase formatted sql

--changeset author:llav3ji2019
create table device (
    id serial primary key,
    common_device_time numeric
);
