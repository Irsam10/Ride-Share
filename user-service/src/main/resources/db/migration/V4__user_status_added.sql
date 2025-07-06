ALTER TABLE t_user
    ADD user_status VARCHAR(1) NULL;
ALTER TABLE t_user
    CHANGE COLUMN user_type user_type VARCHAR(1) NULL;