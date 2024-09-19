DROP TABLE IF EXISTS employees;

CREATE TABLE employees
(
    id integer not null COMMENT '主键ID',
    first_name varchar(255) not null comment '姓氏',
    last_name varchar(255) null DEFAULT NULL COMMENT '名称',
    age integer NULL DEFAULT 0 COMMENT '年龄',
    email_address varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    primary key(id)
);

CREATE TABLE events (
    id int primary key COMMENT '主键',
    param json COMMENT '数据'
)