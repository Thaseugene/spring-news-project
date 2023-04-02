create schema if not exists 'news_service';

use news_service;

create table 'users'
(
    id              int auto_increment not null primary key,
    login           varchar(45)        not null,
    password        varchar(255)       not null,
    email           varchar(255)       not null,
    is_active       tinyint            not null,
    user_details_id int                not null,
    role_id         int                not null,
    foreign key (user_details_id) references user_details (id),
    foreign key (role_id) references roles (id),
    unique (user_details_id, role_id)
)
    engine = InnoDB;


create table 'user_details'
(
    id            int auto_increment not null primary key,
    name          varchar(45)        not null,
    surname       varchar(45)        not null,
    register_date datetime           not null
)
    engine = InnoDB;


create table 'roles'
(
    id   bigint auto_increment not null primary key,
    name varchar(100)          not null
)
    engine = InnoDB;


create table 'news'
(
    id               int auto_increment not null primary key,
    title            varchar(255)       not null,
    brief_news       varchar(255)       not null,
    content          varchar(255)       not null,
    publication_date DATETIME           not null,
    creation_date    DATETIME           not null,
    is_active        tinyint            not null,
    image_path       varchar(255)       not null,
    users_id         int                not null,
    foreign key (users_id) references users (id)
)
    engine = InnoDB;

INSERT INTO `news_service`.`user_details` (`name`, `surname`, `register_date`)
VALUES ('user', 'user', '30.03.2023');

insert into 'roles'
values (1, 'ROLE_ADMIN');
insert into 'roles'
values (2, 'ROLE_USER');