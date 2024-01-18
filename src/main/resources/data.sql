------------ insert user ------------
insert into tbl_user (id, user_name, role, password,version)
values (1, 'test', 'ADMIN', '$2a$10$Jd26qxVe0hPeFx9eZA/t1egEbonMI7JTIdPXa9EURdsPjJiVTFDwG',1);

------------ insert token ------------
insert into tbl_token (id, user_id, token, token_type, expired, revoked,version)
values (1, 1,
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA1NjAyNDAyLCJleHAiOjE3MDU2ODg4MDJ9.cTCpEeWlchgB3KX0cpMRifGXjrMu0RNx2buisNCkLI0',
        'BEARER', false, false,1);
