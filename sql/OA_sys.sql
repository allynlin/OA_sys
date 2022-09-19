drop database if exists OA_sys;
create database OA_sys;
use OA_sys;

create table teacher
(
    uid           char(36)    not null primary key comment '教师编号',
    username      varchar(20) not null unique comment '教师登录名',
    password      varchar(20) not null comment '教师登录密码',
    realeName     varchar(10) not null comment '教师真实姓名',
    gender        char(1)     not null comment '性别',
    tel           char(11)    not null comment '教师联系电话',
    email         varchar(30) not null comment '教师邮箱',
    departmentUid char(36)    not null comment '上级部门编号',
    status        int         not null default 0 comment '状态，0 正常，1禁用',
    create_time   datetime    not null default now() comment '创建时间',
    update_time   datetime    not null default now() comment '更新时间'
    # foreign key (departmentUid) references department (uid)
);

create table department
(
    uid         char(36)    not null primary key comment '部门编号',
    username    varchar(20) not null unique comment '部门登录名',
    password    varchar(20) not null comment '部门登录密码',
    realeName   varchar(10) not null comment '部门真实姓名',
    gender      char(1)     not null comment '性别',
    tel         char(11)    not null comment '部门联系电话',
    email       varchar(30) not null comment '部门邮箱',
    leaderUid   char(36)    not null comment '上级领导编号',
    status      int         not null default 0 comment '状态，0 正常，1禁用',
    create_time datetime    not null default now() comment '创建时间',
    update_time datetime    not null default now() comment '更新时间'
    # foreign key (leaderUid) references leader (uid)
);

create table leader
(
    uid         char(36)    not null primary key comment '领导编号',
    username    varchar(20) not null unique comment '领导登录名',
    password    varchar(20) not null comment '领导登录密码',
    realeName   varchar(10) not null comment '领导真实姓名',
    gender      char(1)     not null comment '性别',
    tel         char(11)    not null comment '领导联系电话',
    email       varchar(30) not null comment '领导邮箱',
    status      int         not null default 0 comment '状态，0 正常，1禁用',
    create_time datetime    not null default now() comment '创建时间',
    update_time datetime    not null default now() comment '更新时间'
);

# 为 teacher 表添加外键
alter table teacher
    add foreign key (departmentUid) references department (uid);
# 为 department 表添加外键
alter table department
    add foreign key (leaderUid) references leader (uid);

create table noticeDepartment
(
    uid         char(36)    not null primary key comment '公告编号',
    type        char(10)    not null comment '公告类型',
    title       varchar(20) not null comment '公告标题',
    content     text        not null comment '公告内容',
    releaseUid  char(36)    not null comment '发布人编号',
    status      int         not null default 0 comment '状态，0 正常，-1 删除,1 置顶',
    create_time datetime    not null default now() comment '创建时间',
    update_time datetime    not null default now() comment '更新时间',
    foreign key (releaseUid) references department (uid)
);

create table noticeLeader
(
    uid         char(36)    not null primary key comment '公告编号',
    type        char(10)    not null comment '公告类型',
    title       varchar(20) not null comment '公告标题',
    content     text        not null comment '公告内容',
    releaseUid  char(36)    not null comment '发布人编号',
    status      int         not null default 0 comment '状态，0 正常，-1 删除,1 置顶',
    create_time datetime    not null default now() comment '创建时间',
    update_time datetime    not null default now() comment '更新时间',
    foreign key (releaseUid) references leader (uid)
);

use OA_sys;
create table changeDepartmentByTeacher
(
    uid           char(36) not null primary key comment '申请编号',
    releaseUid    char(36) not null comment '申请人编号',
    departmentUid char(36) not null comment '申请部门编号',
    changeReason  text     not null comment '申请原因',
    changeFile    text comment '申请文件',
    status        int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time   datetime not null default now() comment '创建时间',
    update_time   datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid),
    foreign key (departmentUid) references department (uid)
);

create table changeDepartmentByDepartment
(
    uid          char(36) not null primary key comment '申请编号',
    releaseUid   char(36) not null comment '申请人编号',
    leaderUid    char(36) not null comment '申请领导编号',
    changeReason text     not null comment '申请原因',
    changeFile   text comment '申请文件',
    status       int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time  datetime not null default now() comment '创建时间',
    update_time  datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references department (uid),
    foreign key (leaderUid) references leader (uid)
);

create table travelReimbursement
(
    uid         char(36) not null primary key comment '申请编号',
    releaseUid  char(36) not null comment '申请人编号',
    type        char(10) not null comment '申请类型',
    destination text     not null comment '申请目的地',
    expenses    text     not null comment '申请费用',
    reason      text     not null comment '申请原因',
    file        text comment '报销单据',
    status      int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid)
);

create table purchasingApplicationTeacher
(
    uid         char(36) not null primary key comment '申请编号',
    releaseUid  char(36) not null comment '申请人编号',
    items       text     not null comment '申请物品',
    price       text     not null comment '申请价格',
    reason      text     not null comment '申请原因',
    status      int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid)
);

create table workReportTeacher
(
    uid         char(36) not null primary key comment '报告编号',
    releaseUid  char(36) not null comment '提交人编号',
    file        text     not null comment '报告文件',
    status      int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid)
);

create table workReportDepartment
(
    uid         char(36) not null primary key comment '报告编号',
    releaseUid  char(36) not null comment '提交人编号',
    file        text     not null comment '报告文件',
    status      int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references department (uid)
);

create table leaveApplyTeacher
(
    uid         char(36) not null primary key comment '申请编号',
    releaseUid  char(36) not null comment '请假人编号',
    reason      text     not null comment '请假原因',
    leaveTime   text     not null comment '请假时间',
    status      int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time datetime not null default now() comment '创建时间',
    update_time datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid)
);

create table addTeacher
(
    uid           char(36) not null primary key comment '申请编号',
    releaseUid    char(36) not null comment '申请人编号',
    username      char(32) not null comment '教师登录用户名',
    realeName     char(32) not null comment '教师真实姓名',
    gender        char(1)  not null comment '教师性别',
    tel           char(11) not null comment '教师电话',
    email         char(32) not null comment '教师邮箱',
    departmentUid char(36) not null comment '教师部门',
    status        int      not null default 0 comment '状态，0 正常，-1 删除,1 通过',
    count         int      not null default 0 comment '审批次数',
    create_time   datetime not null default now() comment '创建时间',
    update_time   datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid),
    foreign key (departmentUid) references department (uid)
);