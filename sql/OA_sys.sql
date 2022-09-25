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
    nextUid       char(36) not null comment '下一步处理人编号',
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
    count        int      not null default 0 comment '审批次数',
    nextUid      char(36) not null comment '下一步处理人编号',
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
    count       int      not null default 0 comment '审批次数',
    nextUid     char(36) not null comment '下一步处理人编号',
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
    count       int      not null default 0 comment '审批次数',
    nextUid     char(36) not null comment '下一步处理人编号',
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
    count       int      not null default 0 comment '审批次数',
    nextUid     char(36) not null comment '下一步处理人编号',
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
    count       int      not null default 0 comment '审批次数',
    nextUid     char(36) not null comment '下一步处理人编号',
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
    count       int      not null default 0 comment '审批次数',
    nextUid     char(36) not null comment '下一步处理人编号',
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
    nextUid       char(36) not null comment '下一步处理人编号',
    create_time   datetime not null default now() comment '创建时间',
    update_time   datetime not null default now() comment '更新时间',
    foreign key (releaseUid) references teacher (uid),
    foreign key (departmentUid) references department (uid)
);

INSERT INTO `leader`
VALUES ('37c1647c-0c0f-465f-977d-3e3f027a7ad3', 'lt', 'adminadmin', '龙涛', '男', '15898764567', '123@qq.com', 0,
        '2022-09-14 20:15:06', '2022-09-14 20:15:06');
INSERT INTO `leader`
VALUES ('c1e7b1ab-4c0c-489f-a8d7-c76317436b75', 'ly', 'adminadmin', '刘洋', '男', '15845677654', '123@qq.com', 0,
        '2022-09-14 20:15:44', '2022-09-14 20:15:44');
INSERT INTO `leader`
VALUES ('c340cd4a-df60-4e19-bf1b-a990f505bbe6', 'jmk', 'adminadmin', '小红', '女', '13987655678', 'asd@163.com', 0,
        '2022-09-14 20:16:32', '2022-09-14 20:16:32');

INSERT INTO `department`
VALUES ('285040e7-dbe9-4666-90c0-70b8d0af84fb', 'tp', 'adminadmin', '唐频', '男', '15812344321', 'aaa@qq.com',
        '37c1647c-0c0f-465f-977d-3e3f027a7ad3', 0, '2022-09-14 20:17:38', '2022-09-14 20:17:38');
INSERT INTO `department`
VALUES ('660a58b3-1b68-4348-beb5-5df5e9e5f9ea', 'sy', 'adminadmin', '佘远', '男', '13998766789', 'sy@qq.com',
        'c1e7b1ab-4c0c-489f-a8d7-c76317436b75', 0, '2022-09-14 20:18:13', '2022-09-14 20:18:13');
INSERT INTO `department`
VALUES ('b752d7a0-8373-4c4c-864f-a85d9999f433', 'rr', 'adminadmin', '任睿', '男', '15867894567', 'rr@qq.com',
        'c340cd4a-df60-4e19-bf1b-a990f505bbe6', 0, '2022-09-14 20:19:03', '2022-09-14 20:19:03');

INSERT INTO `teacher`
VALUES ('b436bc05-37ec-49ed-b374-2e84b683b380', 'ww', 'adminadmin', '王五', '男', '18967899876', 'ww@163.com',
        '660a58b3-1b68-4348-beb5-5df5e9e5f9ea', 0, '2022-09-14 20:20:52', '2022-09-14 20:20:52');
INSERT INTO `teacher`
VALUES ('e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'zs', 'adminadmin', '张三', '男', '15834566543', 'zs@qq.com',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 0, '2022-09-14 20:19:50', '2022-09-14 20:19:50');
INSERT INTO `teacher`
VALUES ('ed4160ed-59d6-4c59-b17f-5584a920135c', 'ls', 'adminadmin', '李四', '男', '15856781234', 'ls@outlook.com',
        '285040e7-dbe9-4666-90c0-70b8d0af84fb', 0, '2022-09-14 20:20:17', '2022-09-14 20:20:17');