create table if not exists event (
    id int primary key auto_increment,
    status int not null comment '事件状态 0=todo,1=done,2=cancel',
    name varchar not null comment '事件名称 e.g. fetchA,fetchB,...',
    param json not null comment '事件参数，可用于查询',
    context json comment '事件上下文',
    update_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);