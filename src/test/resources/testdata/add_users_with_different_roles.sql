INSERT INTO users (first_name, last_name, father_name, email, password, user_status)
VALUES
    ('Igor', 'Yakubovich', 'Sergeevich', 'user@gmail.com', '$2a$12$nU3NTASAvpso5NuAr..2UOQL15kPiJ.IiwFDVZVMP86TUvZrSrf0m', 'NOT_CONFIRMED'),
    ('Alexey', 'Tagil', 'Ivanovich','user-user@mail.com', '$2a$12$Xd6uSdDeGUktWdcVm7MT7e.N/RGEkwK58XaiFuDTUw4FUqe/fpMN6', 'CONFIRMED');

INSERT INTO users_roles (user_id, role_id)
VALUES
    ((SELECT u.id FROM users u WHERE u.email = 'user@gmail.com'), (SELECT r.id FROM roles r WHERE r.role_name = 'ADMIN')),
    ((SELECT u.id FROM users u WHERE u.email = 'user-user@mail.com'), (SELECT r.id FROM roles r WHERE r.role_name = 'USER'));
