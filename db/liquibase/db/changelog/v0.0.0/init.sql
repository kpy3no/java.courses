--liquibase formatted sql

--changeset kuzmin-ea:hibernate-lesson2-first context:dev
create table "group" (
                                ID bigserial not null
                                    constraint GROUP_PARAM_PK
                                        primary key,
                                NAME VARCHAR(20) NOT NULL
                                    constraint UQ_GROUP_NAME
                                        unique,
                                DIRECTION VARCHAR(50) NOT NULL
);

comment on table "group" is 'Группа';
comment on column "group".ID is 'Идентификатор записи';
comment on column "group".NAME is 'Наименование группы';
comment on column "group".DIRECTION is 'Направление группы';

------------------------------------------------
create table DISCIPLINE (
                         id bigserial not null
                             constraint DISCIPLINE_param_pk
                                 primary key,
                         CODE integer NOT NULL
                             constraint UQ_DISCIPLINE_CODE
                                 unique,
                         DESCRIPTION VARCHAR(100) NOT NULL
);

comment on table DISCIPLINE is 'Дисциплина';
comment on column DISCIPLINE.ID is 'Код записи';
comment on column DISCIPLINE.CODE is 'Код дисциплины';
comment on column DISCIPLINE.DESCRIPTION is 'Описание дисциплины';

------------------------------------------------
create table CONTACT_INFO (
                              ID bigserial not null
                                  constraint CONTACT_INFO_PARAM_PK
                                      primary key,
                              email VARCHAR(20) NOT NULL,
                              telephone_number VARCHAR(20) NOT NULL
);

comment on table CONTACT_INFO is 'Связь студентов и дисциплины';
comment on column CONTACT_INFO.ID is 'Идентификатор записи';
comment on column CONTACT_INFO.email is 'Студент';
comment on column CONTACT_INFO.telephone_number is 'Дисциплина';