DROP TABLE IF EXISTS pe_user;

CREATE TABLE pe_user
(
    id integer not null COMMENT '主键ID',
    username varchar(255) not null comment '用户名',
    password varchar(255) not null COMMENT '用户密码',
    real_name varchar(255) NULL DEFAULT null COMMENT '姓名',
    mobile varchar(255) NULL DEFAULT NULL COMMENT '手机',
    email varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    primary key(id)
);