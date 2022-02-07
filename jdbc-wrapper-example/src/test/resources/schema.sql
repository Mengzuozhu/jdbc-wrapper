-- auto-generated definition
create table user
(
    id          bigint auto_increment,
    name        varchar(20)                         not null,
    remark_msg  varchar(64)                         null,
    age         int                                 null,
    modify_time timestamp default CURRENT_TIMESTAMP not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

