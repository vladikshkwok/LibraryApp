--liquibase formatted sql
--changeset vladikshk:update_authors_password

update author set username=lower(name), password='test_password';