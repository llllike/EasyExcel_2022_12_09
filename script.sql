create table mybatisplus_db.user
(
    id       int auto_increment,
    name     varchar(255)     not null,
    password varchar(255)     not null,
    age      int(5)           not null,
    tel      varchar(40)      not null,
    deleted  int(1) default 0 not null,
    version  int(20)          not null,
    sex      int(1)           not null,
    constraint user_id_uindex
        unique (id),
    constraint user_tel_uindex
        unique (tel)
);

alter table mybatisplus_db.user
    add primary key (id);


