-- можно создавать руками, или hiber автоматом создаст таблицу по классу Car
create table car
(
    id    bigserial not null
        constraint car_param_pk
            primary key,
    model varchar(100)
);
