create table engine
(
    id   serial primary key,
    name varchar(50)
);

create table car
(
    id        serial primary key,
    name      varchar(50),
    engine_id int not null unique references engine (id)
);

create table owner
(
    id      serial primary key,
    name    varchar(50),
    user_id int not null references auto_user (id)
);

create table history
(
    id      serial primary key,
    start_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    end_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    owner_id int not null references owner (id),
    car_id   int not null references car (id)
);

create table auto_post
(
    id           serial primary key,
    description  varchar not null,
    created      TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    auto_user_id int references auto_user (id),
    car_id       int references car (id)
);

