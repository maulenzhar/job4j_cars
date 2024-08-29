create table photo
(
    id   serial primary key,
    name varchar not null,
    path varchar not null,
    auto_post_photo_id int REFERENCES auto_post(id)
);