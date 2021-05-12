create table users
(
    id          bigserial not null
        constraint users_param_pk
            primary key,
    name        varchar(30),
    surnamename varchar(30)
);

create table comment
(
    id      bigserial     not null
        constraint comment_param_pk
            primary key,
    user_id bigint        not null
        constraint comment_user_fk
            references users,
    text    varchar(1000) not null
);


insert into users (id, name, surnamename)
values (1,'Вася','Васильев'),
       (2,'Петр','Петренко');

insert into comment (id, user_id, text)
values (1,1,'my comment 1'),
       (2,2,'my comment 2'),
       (3,1,'my comment 1 2')
