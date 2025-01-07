SELECT acc.id , username, tar.id, tar.id_role, tr.role_name 
FROM public.t_accounts acc 
LEFT JOIN public.t_accounts_roles tar ON acc.id = tar.id_account 
LEFT JOIN public.t_roles tr ON tar.id_role =tr.id
ORDER BY tar.id_role;

SELECT acc.id , username, tar.id, tar.id_role, tr.role_name 
FROM public.t_accounts acc 
INNER JOIN public.t_accounts_roles tar ON acc.id = tar.id_account 
INNER JOIN public.t_roles tr ON tar.id_role =tr.id
ORDER BY tar.id_role;
