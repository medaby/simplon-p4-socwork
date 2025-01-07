

CREATE TABLE t_roles(
	id INT GENERATED ALWAYS AS IDENTITY,
	role_name VARCHAR(255) NOT NULL DEFAULT 'MANAGER',
	flag INT NOT NULL CHECK (flag IN (0, 1)),
	CONSTRAINT t_roles_pkey PRIMARY KEY (id),
    CONSTRAINT t_roles_ukey UNIQUE (role_name)
);

CREATE UNIQUE INDEX unique_flag_one
ON t_roles (flag)
WHERE flag = 1;


CREATE TABLE t_accounts(
	id INT GENERATED ALWAYS AS IDENTITY,
	username VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT t_accounts_pkey PRIMARY KEY (id),
    CONSTRAINT t_accounts_ukey UNIQUE (username)

);

CREATE TABLE t_accounts_roles(
    id INT GENERATED ALWAYS AS IDENTITY,
    id_role INT,
    id_account INT,
    CONSTRAINT t_accounts_roles_pkey PRIMARY KEY (id),
    CONSTRAINT t_accounts_roles_ukey UNIQUE (id_role, id_account),
    CONSTRAINT t_accounts_roles_fkey FOREIGN KEY (id_role) REFERENCES t_roles(id),
    CONSTRAINT t_accounts_accounts_fkey FOREIGN KEY (id_account) REFERENCES t_accounts(id)
);