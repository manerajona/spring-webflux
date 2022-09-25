create table if not exists jedi
(
    id     UUID default random_uuid() not null primary key,
    name   varchar(255),
    gender varchar(255),
    birth_year varchar(255),
    planet varchar(255),
    url varchar(255)
);
