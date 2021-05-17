--liquibase formatted sql

--changeset kuzmin-ea:hibernate-lesson2-second context:dev
create table STUDENT (
                                ID bigserial not null
                                    constraint STUDENT_PARAM_PK
                                        primary key,
                                NAME VARCHAR(30) NOT NULL,
                                "group" bigint not null
                                    constraint STUDENT_TYPE_FK
                                    references "group",
                                contact bigint not null
                                    constraint CONTACT_TYPE_FK
                                        references CONTACT_INFO
);

comment on table STUDENT is 'Группа';
comment on column STUDENT.ID is 'Идентификатор записи';
comment on column STUDENT.NAME is 'Имя студента';
comment on column STUDENT."group" is 'Группа';
comment on column STUDENT.CONTACT is 'Контактная информация';

------------------------------------------------
create table STUDENT_DISCIPLINE (
                         ID bigserial not null
                             constraint STUDENT_DISCIPLINE_PARAM_PK
                                 primary key,
                         STUDENT bigint not null
                             constraint STUDENT_TYPE_FK
                                 references STUDENT,
                         DISCIPLINE bigint not null
                             constraint DISCIPLINE_TYPE_FK
                                 references DISCIPLINE
);

comment on table STUDENT_DISCIPLINE is 'Связь студентов и дисциплины';
comment on column STUDENT_DISCIPLINE.ID is 'Идентификатор записи';
comment on column STUDENT_DISCIPLINE.STUDENT is 'Студент';
comment on column STUDENT_DISCIPLINE.DISCIPLINE is 'Дисциплина';

