-- INSERT user_roles -------------------------------------------
INSERT INTO user_roles (id, name, description)
VALUES (1, 'ADMIN','Description for ADMIN');
INSERT INTO user_roles (id, name, description)
VALUES (2, 'MENTOR','Description for MENTOR');
INSERT INTO user_roles (id, name, description)
VALUES (3, 'USER','Description for USER');

-- INSERT workshop_categories -------------------------------------------
INSERT INTO workshop_categories (id, name, description)
VALUES (1, 'WATERCOLOR', 'Description for WATERCOLOR');
INSERT INTO workshop_categories (id, name, description)
VALUES (2, 'ACRYLIC', 'Description for ACRYLIC');
INSERT INTO workshop_categories (id, name, description)
VALUES (3, 'SOFT_PASTELS', 'Description for SOFT_PASTELS');
INSERT INTO workshop_categories (id, name, description)
VALUES (4, 'GRAPHITE_PENCILS', 'Description for GRAPHITE_PENCILS');


-- INSERT experience_level -------------------------------------------
INSERT INTO experience_level (id, name, description)
VALUES (1, 'BEGINNER', 'Description for BEGINNER');
INSERT INTO experience_level (id, name, description)
VALUES (2, 'INTERMEDIATE', 'Description for INTERMEDIATE');
INSERT INTO experience_level (id, name, description)
VALUES (3, 'ADVANCED', 'Description for ADVANCED');

-- INSERT users---------------------------------
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (1,
        'https://www.facebook.com/Ani.Ianeva/',
        'Ana',
        'https://www.instagram.com/ana.yaneva/',
        'Hristeva',
        'https://www.linkedin.com/in/ana-hristeva-9b7475157/',
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'ana_yaneva@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (2,
        null,
        'Ivan',
        null,
        'Ivanov',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Ivan-Ivanov@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (3,
        null,
        'Petar',
        null,
        'Petrov',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Petar-Petrov@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (4,
        null,
        'Sasho',
        null,
        'Sashev',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Sasho-Sashev@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (5,
        null,
        'Dan',
        null,
        'Danev',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Dan-Danev@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (6,
        'https://www.facebook.com/Matea.Ianeva/',
        'Matea',
        'https://www.instagram.com/ana.yaneva/',
        'Yaneva',
        'https://www.linkedin.com/in/ana-hristeva-9b7475157/',
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Matea-Yaneva@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (7,
        null,
        'Todor',
        null,
        'Todorov',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Todor-Todorov@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (8,
        null,
        'Katia',
        null,
        'Kateva',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Katia-Kateva@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (9,
        null,
        'Misha',
        null,
        'Misheva',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Misha-Misheva@abv.bg');
INSERT INTO users (id,
                   facebook,
                   first_name,
                   instagram,
                   last_name,
                   linked_in,
                   password,
                   username)
VALUES (10,
        null,
        'Vania',
        null,
        'Vaneva',
        null,
        '725bda1467f65b3050b9c096227626178d7400f3e9621753f6e04451739c1620287c9b0a99514b99',
        'Vania-Vaneva@abv.bg');

-- INSERT users_user_roles -------------------------------------------
INSERT INTO users_user_roles (user_entity_id, user_roles_id)
VALUES (1, 1);
INSERT INTO users_user_roles (user_entity_id, user_roles_id)
VALUES (1, 2);
INSERT INTO users_user_roles (user_entity_id, user_roles_id)
VALUES (6, 2);
INSERT INTO users_user_roles (user_entity_id, user_roles_id)
VALUES (5, 2);

-- INSERT media -------------------------------------------
INSERT INTO media (id, name,public_id, url)
VALUES (1,'video','zunz7jfigkentsf4xy3n','https://res.cloudinary.com/dmumqnbm0/video/upload/v1659978880/zunz7jfigkentsf4xy3n.mp4');
INSERT INTO media (id, name,public_id, url)
VALUES (2,'referencePhoto','r6xpnrxmp4xsfaac3wqb','http://res.cloudinary.com/dmumqnbm0/image/upload/v1659974382/r6xpnrxmp4xsfaac3wqb.jpg');
INSERT INTO media (id, name,public_id, url)
VALUES (3,'finalPhoto','jwpvze3mrn0huxlf1cq7','http://res.cloudinary.com/dmumqnbm0/image/upload/v1659974384/jwpvze3mrn0huxlf1cq7.jpg');

-- INSERT online_workshops -------------------------------------------
INSERT INTO online_workshops (id,
    description,
duration,
name,
status,
category_id,
experience_level_id,
final_photo_id,
mentor_id,
reference_photo_id,
video_id)
VALUES (1,'asdm,fa,NHDJSAKNDKNSA.,ASMDASD',null,'chushki','PENDING',3,2,3,1,2,1);


