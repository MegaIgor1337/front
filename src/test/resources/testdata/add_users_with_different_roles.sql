INSERT INTO users (first_name, last_name, father_name, email, password)
VALUES
    ('Igor', 'Yakubovich', 'Sergeevich', 'admin@mail.ru', '$2a$12$aamBl10n5CQ8cSM3urpjpenJir.uZXP0pguKRaj2cvrBqRKm4ob5W'),
    ('Alexey', 'Tagil', 'Ivanovich','user-user@mail.com', '$2a$12$Xd6uSdDeGUktWdcVm7MT7e.N/RGEkwK58XaiFuDTUw4FUqe/fpMN6'),
    ('Alexey', 'Tagil', 'Ivanovich','unconfirmed-user@mail.com', '$2a$12$Xd6uSdDeGUktWdcVm7MT7e.N/RGEkwK58XaiFuDTUw4FUqe/fpMN6');

INSERT INTO users_roles (user_id, role_id)
VALUES
    ((SELECT u.id FROM users u WHERE u.email = 'admin@mail.ru'), (SELECT r.id FROM roles r WHERE r.role_name = 'ROLE_ADMIN')),
    ((SELECT u.id FROM users u WHERE u.email = 'user-user@mail.com'), (SELECT r.id FROM roles r WHERE r.role_name = 'ROLE_USER')),
    ((SELECT u.id FROM users u WHERE u.email = 'unconfirmed-user@mail.com'), (SELECT r.id FROM roles r WHERE r.role_name = 'ROLE_UNCONFIRMED_USER'));
