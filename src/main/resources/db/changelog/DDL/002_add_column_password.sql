--liquibase formatted sql
--changeset vladikshk:add_column_password

alter table Author
add column username varchar(255) not null default '',
add column password varchar(255) not null default '';