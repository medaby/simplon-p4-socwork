INSERT INTO public.t_roles
(role_name, flag)
VALUES
('ADMIN', 0),
('HR', 0);

INSERT INTO public.t_accounts_roles
(id_account, id_role) 
VALUES
(5,3);


SELECT id, role_name, flag
FROM public.t_roles;