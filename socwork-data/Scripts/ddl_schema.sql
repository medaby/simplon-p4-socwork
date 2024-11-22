CREATE TABLE t_accounts(
	id INT GENERATED ALWAYS AS IDENTITY,
	username VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT t_accounts_pkey PRIMARY KEY (id),
    CONSTRAINT t_accounts_ukey UNIQUE (username)
);