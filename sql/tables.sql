-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                       null comment '用户昵称',
    useraccount  varchar(256)                       null comment '账号',
    avataUrl     varchar(1024)                      null comment '头像',
    gender       tinyint                            null comment '性别',
    userpassword varchar(512)                       not null,
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userstatus   int                                null comment '是否有效',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint  default 0                 null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色',
    planteCode   varchar(512)                       not null comment '星球编号'
)
    comment '用户表';