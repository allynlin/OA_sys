DROP TABLE IF EXISTS `addteacher`;

CREATE TABLE `addteacher`
(
    `uid`           char(36) NOT NULL COMMENT '申请编号',
    `releaseUid`    char(36) NOT NULL COMMENT '申请人编号',
    `username`      char(32) NOT NULL COMMENT '教师登录用户名',
    `realeName`     char(32) NOT NULL COMMENT '教师真实姓名',
    `gender`        char(1)  NOT NULL COMMENT '教师性别',
    `tel`           char(11) NOT NULL COMMENT '教师电话',
    `email`         char(32) NOT NULL COMMENT '教师邮箱',
    `departmentUid` char(36) NOT NULL COMMENT '教师部门',
    `status`        int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`         int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`       char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    KEY `departmentUid` (`departmentUid`),
    CONSTRAINT `addteacher_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `teacher` (`uid`),
    CONSTRAINT `addteacher_ibfk_2` FOREIGN KEY (`departmentUid`) REFERENCES `department` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `changedepartmentbydepartment`;

CREATE TABLE `changedepartmentbydepartment`
(
    `uid`          char(36) NOT NULL COMMENT '申请编号',
    `releaseUid`   char(36) NOT NULL COMMENT '申请人编号',
    `leaderUid`    char(36) NOT NULL COMMENT '申请领导编号',
    `changeReason` text     NOT NULL COMMENT '申请原因',
    `changeFile`   text COMMENT '申请文件',
    `status`       int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`        int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`      char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    KEY `leaderUid` (`leaderUid`),
    CONSTRAINT `changedepartmentbydepartment_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `department` (`uid`),
    CONSTRAINT `changedepartmentbydepartment_ibfk_2` FOREIGN KEY (`leaderUid`) REFERENCES `leader` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `changedepartmentbyteacher`;

CREATE TABLE `changedepartmentbyteacher`
(
    `uid`           char(36) NOT NULL COMMENT '申请编号',
    `releaseUid`    char(36) NOT NULL COMMENT '申请人编号',
    `departmentUid` char(36) NOT NULL COMMENT '申请部门编号',
    `changeReason`  text     NOT NULL COMMENT '申请原因',
    `status`        int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`         int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`       char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    KEY `departmentUid` (`departmentUid`),
    CONSTRAINT `changedepartmentbyteacher_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `teacher` (`uid`),
    CONSTRAINT `changedepartmentbyteacher_ibfk_2` FOREIGN KEY (`departmentUid`) REFERENCES `department` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department`
(
    `uid`         char(36)    NOT NULL COMMENT '部门编号',
    `username`    varchar(20) NOT NULL COMMENT '部门登录名',
    `password`    varchar(20) NOT NULL COMMENT '部门登录密码',
    `realeName`   varchar(10) NOT NULL COMMENT '部门真实姓名',
    `gender`      char(1)     NOT NULL COMMENT '性别',
    `tel`         char(11)    NOT NULL COMMENT '部门联系电话',
    `email`       varchar(30) NOT NULL COMMENT '部门邮箱',
    `leaderUid`   char(36)    NOT NULL COMMENT '上级领导编号',
    `status`      int         NOT NULL DEFAULT '0' COMMENT '状态，0 正常，1禁用',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    UNIQUE KEY `username` (`username`),
    KEY `leaderUid` (`leaderUid`),
    CONSTRAINT `department_ibfk_1` FOREIGN KEY (`leaderUid`) REFERENCES `leader` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `filename`;

CREATE TABLE `filename`
(
    `uid`         char(36) NOT NULL COMMENT '文件唯一UID',
    `ReleaseUid`  char(36) NOT NULL COMMENT '上传人编号',
    `TableUid`    char(36) NOT NULL COMMENT '表编号',
    `RowUid`      char(36)          DEFAULT NULL COMMENT '行编号',
    `FileName`    text     NOT NULL COMMENT '文件名',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `oldFileName` text     NOT NULL COMMENT '原文件名',
    PRIMARY KEY (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `leader`;

CREATE TABLE `leader`
(
    `uid`         char(36)    NOT NULL COMMENT '领导编号',
    `username`    varchar(20) NOT NULL COMMENT '领导登录名',
    `password`    varchar(20) NOT NULL COMMENT '领导登录密码',
    `realeName`   varchar(10) NOT NULL COMMENT '领导真实姓名',
    `gender`      char(1)     NOT NULL COMMENT '性别',
    `tel`         char(11)    NOT NULL COMMENT '领导联系电话',
    `email`       varchar(30) NOT NULL COMMENT '领导邮箱',
    `status`      int         NOT NULL DEFAULT '0' COMMENT '状态，0 正常，1禁用',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `leave`;

CREATE TABLE `leave`
(
    `uid`           char(36) NOT NULL COMMENT '申请编号',
    `releaseUid`    char(36) NOT NULL COMMENT '请假人编号',
    `reason`        text     NOT NULL COMMENT '请假原因',
    `start_time`    text     NOT NULL COMMENT '请假时间',
    `end_time`      text     NOT NULL COMMENT '销假时间',
    `status`        int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`         int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`       char(36) NOT NULL COMMENT '下一步处理人编号',
    `reject_reason` text COMMENT '驳回原因',
    `create_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `noticedepartment`;

CREATE TABLE `noticedepartment`
(
    `uid`         char(36)    NOT NULL COMMENT '公告编号',
    `type`        char(10)    NOT NULL COMMENT '公告类型',
    `title`       varchar(20) NOT NULL COMMENT '公告标题',
    `content`     text        NOT NULL COMMENT '公告内容',
    `releaseUid`  char(36)    NOT NULL COMMENT '发布人编号',
    `status`      int         NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 置顶',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    CONSTRAINT `noticedepartment_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `department` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `noticeleader`;

CREATE TABLE `noticeleader`
(
    `uid`         char(36)    NOT NULL COMMENT '公告编号',
    `type`        char(10)    NOT NULL COMMENT '公告类型',
    `title`       varchar(20) NOT NULL COMMENT '公告标题',
    `content`     text        NOT NULL COMMENT '公告内容',
    `releaseUid`  char(36)    NOT NULL COMMENT '发布人编号',
    `status`      int         NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 置顶',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    CONSTRAINT `noticeleader_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `leader` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `process`;

CREATE TABLE `process`
(
    `uid`         char(36) NOT NULL COMMENT '唯一编号',
    `name`        text     NOT NULL COMMENT '流程名称',
    `process`     text     NOT NULL COMMENT '流程',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `purchasingapplicationteacher`;

CREATE TABLE `purchasingapplicationteacher`
(
    `uid`         char(36) NOT NULL COMMENT '申请编号',
    `releaseUid`  char(36) NOT NULL COMMENT '申请人编号',
    `items`       text     NOT NULL COMMENT '申请物品',
    `price`       text     NOT NULL COMMENT '申请价格',
    `reason`      text     NOT NULL COMMENT '申请原因',
    `status`      int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`       int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`     char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    CONSTRAINT `purchasingapplicationteacher_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `teacher` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher`
(
    `uid`           char(36)    NOT NULL COMMENT '教师编号',
    `username`      varchar(20) NOT NULL COMMENT '教师登录名',
    `password`      varchar(20) NOT NULL COMMENT '教师登录密码',
    `realeName`     varchar(10) NOT NULL COMMENT '教师真实姓名',
    `gender`        char(1)     NOT NULL COMMENT '性别',
    `tel`           char(11)    NOT NULL COMMENT '教师联系电话',
    `email`         varchar(30) NOT NULL COMMENT '教师邮箱',
    `departmentUid` char(36)    NOT NULL COMMENT '上级部门编号',
    `status`        int         NOT NULL DEFAULT '0' COMMENT '状态，0 正常，1禁用',
    `create_time`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    UNIQUE KEY `username` (`username`),
    KEY `departmentUid` (`departmentUid`),
    CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`departmentUid`) REFERENCES `department` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `travelreimbursement`;

CREATE TABLE `travelreimbursement`
(
    `uid`         char(36) NOT NULL COMMENT '申请编号',
    `releaseUid`  char(36) NOT NULL COMMENT '申请人编号',
    `destination` text     NOT NULL COMMENT '申请目的地',
    `expenses`    text     NOT NULL COMMENT '申请费用',
    `reason`      text     NOT NULL COMMENT '申请原因',
    `status`      int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`       int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`     char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    CONSTRAINT `travelreimbursement_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `teacher` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `workreportdepartment`;

CREATE TABLE `workreportdepartment`
(
    `uid`         char(36) NOT NULL COMMENT '报告编号',
    `releaseUid`  char(36) NOT NULL COMMENT '提交人编号',
    `file`        text     NOT NULL COMMENT '报告文件',
    `status`      int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`       int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`     char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    CONSTRAINT `workreportdepartment_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `department` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `workreportteacher`;

CREATE TABLE `workreportteacher`
(
    `uid`         char(36) NOT NULL COMMENT '报告编号',
    `releaseUid`  char(36) NOT NULL COMMENT '提交人编号',
    `status`      int      NOT NULL DEFAULT '0' COMMENT '状态，0 正常，-1 删除,1 通过',
    `count`       int      NOT NULL DEFAULT '0' COMMENT '审批次数',
    `nextUid`     char(36) NOT NULL COMMENT '下一步处理人编号',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`uid`),
    KEY `releaseUid` (`releaseUid`),
    CONSTRAINT `workreportteacher_ibfk_1` FOREIGN KEY (`releaseUid`) REFERENCES `teacher` (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;



INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('测试测试', '0', '2022-10-02 18:59:50', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '023b3994-04ca-4b68-b60a-6bc3bbe7160e', '2022-10-02 19:00:48');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-02 18:40:02', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '0dcf7361-6ef6-4475-a464-fb2dec2ffa45', '2022-10-02 18:47:59');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('test', '0', '2022-10-09 16:13:27', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '165ba680-693d-4357-b5ea-270c74652711', '2022-10-09 17:43:43');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('换', '0', '2022-10-02 19:46:03', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        '285040e7-dbe9-4666-90c0-70b8d0af84fb', 'f4f5ccee-058b-4111-bd45-ac8cf85c2104', '-1',
        '179522d2-da5e-45e1-884a-defb7a8f0bb6', '2022-10-02 19:47:46');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-21 08:13:36', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        '37c1647c-0c0f-465f-977d-3e3f027a7ad3', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '20ac3241-a2cd-424f-8c37-4ff7ca1d6f18', '2022-10-21 08:19:38');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-03 16:30:26', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '2b7badfa-7d9b-4cba-95ba-33644a63c271', '2022-10-03 16:30:37');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('Test', '0', '2022-10-19 19:27:05', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        '37c1647c-0c0f-465f-977d-3e3f027a7ad3', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '2bf3a5c9-bf49-4d0c-ab17-326f3882f392', '2022-10-21 08:07:08');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('test', '0', '2022-10-02 19:21:24', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '3148da70-bbc1-40bd-a2d2-06a1d9db1d69', '2022-10-02 19:36:13');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-18 17:26:43', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '35c1d497-9467-4231-b955-4b9b5e79a706', '2022-10-19 19:23:03');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-04 19:50:32', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '37159afb-d5d7-44e0-9b5c-e12c9a50ab42', '2022-10-05 17:10:45');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-03 16:01:57', 'b752d7a0-8373-4c4c-864f-a85d9999f433',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '3fadaaf1-25f9-46f1-96bf-22012c73149d', '2022-10-03 16:02:09');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-04 18:40:29', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '4892514f-41c3-4e1f-a3f1-581399ea0f79', '2022-10-04 18:40:41');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1231', '0', '2022-10-02 18:52:49', 'b752d7a0-8373-4c4c-864f-a85d9999f433',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '4ef18c08-4e62-449a-a281-cff104f3ec44', '2022-10-02 18:52:58');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-03 16:30:58', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '50b6731b-61b1-43c7-b34b-8badbd74f401', '2022-10-03 16:31:06');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-05 18:51:40', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '63c19783-446f-470b-8f2f-2cd3c657748d', '2022-10-07 18:00:38');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('你好啊，我先测试游戏啊', '0', '2022-10-02 18:48:36', 'b752d7a0-8373-4c4c-864f-a85d9999f433',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '74026c23-ad06-4052-b01e-a79273077a02', '2022-10-02 18:48:59');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-04 13:16:26', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        '8c6b01ad-b45f-4a71-9ca5-fffaa8a62a2b', '2022-10-04 13:16:38');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('测试变更原因', '0', '2022-10-01 12:17:30', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'a3bc7538-e873-4901-b3cc-8f2fa07ed5b2', '2022-10-02 16:12:45');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-03 16:31:30', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'ac021f1f-9967-4a79-bde5-a4805cbd3abe', '2022-10-03 16:31:40');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-04 18:38:55', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'b248f670-b80b-4880-af6c-9a87d0be64c5', '2022-10-04 18:40:11');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-04 19:11:00', 'b752d7a0-8373-4c4c-864f-a85d9999f433',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'c99ad268-f006-4b29-bf2a-4261e1ce6539', '2022-10-04 19:38:04');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-21 08:31:19', 'b752d7a0-8373-4c4c-864f-a85d9999f433',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '0',
        'cf251b59-765e-4b6f-a393-8e28ba12ac8d', '2022-10-21 08:31:19');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-11 14:53:44', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'd2699295-5e23-4c38-9552-4627c778c4b0', '2022-10-13 20:24:37');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('12', '0', '2022-10-03 15:55:03', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'e3e33603-70ac-495f-9031-af56440c262e', '2022-10-03 15:55:09');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-07 19:25:20', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'e43cba2f-5540-4ae7-899d-9590a3195474', '2022-10-09 16:13:09');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('test', '0', '2022-10-04 12:34:44', '285040e7-dbe9-4666-90c0-70b8d0af84fb',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'eb051a2b-d509-4512-b4bb-23d50aec21ea', '2022-10-04 12:35:00');
INSERT INTO `changedepartmentbyteacher` (`changeReason`, `count`, `create_time`, `departmentUid`, `nextUid`,
                                         `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('1212', '0', '2022-10-04 18:43:12', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea',
        'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1',
        'fc69d434-1274-44c2-9281-eabc3443fcbe', '2022-10-04 18:57:41');

INSERT INTO `department` (`create_time`, `email`, `gender`, `leaderUid`, `password`, `realeName`, `status`, `tel`,
                          `uid`, `update_time`, `username`)
VALUES ('2022-09-14 20:17:38', 'aaa@qq.com', '男', '37c1647c-0c0f-465f-977d-3e3f027a7ad3', 'adminadmin', '测试部门1',
        '0', '15812344321', '285040e7-dbe9-4666-90c0-70b8d0af84fb', '2022-09-14 20:17:38', 'test1');
INSERT INTO `department` (`create_time`, `email`, `gender`, `leaderUid`, `password`, `realeName`, `status`, `tel`,
                          `uid`, `update_time`, `username`)
VALUES ('2022-09-14 20:18:13', 'sy@qq.com', '男', 'c1e7b1ab-4c0c-489f-a8d7-c76317436b75', 'adminadmin', '测试部门2',
        '0', '13998766789', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea', '2022-09-14 20:18:13', 'test2');
INSERT INTO `department` (`create_time`, `email`, `gender`, `leaderUid`, `password`, `realeName`, `status`, `tel`,
                          `uid`, `update_time`, `username`)
VALUES ('2022-09-14 20:19:03', 'rr@qq.com', '男', 'c340cd4a-df60-4e19-bf1b-a990f505bbe6', 'adminadmin', '测试部门3',
        '0', '15867894567', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '2022-09-14 20:19:03', 'test3');

INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-19 19:33:40', '0e3bde25-9a0d-4859-aef4-929be4bee3b1.jpg', '0e87dcf54635fa233dda71da6136b40d.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '0fe5bf8e-3418-49a6-9733-e79cf005063a', 'travelreimbursement',
        '0e3bde25-9a0d-4859-aef4-929be4bee3b1', '2022-10-19 19:34:41');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-09 17:39:24', '13a336b9-dff3-4e28-9a5a-b2fabd1425b2.jpg', '4e2b7ba0015f9486cb8d48e29feeb1c6.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'f7743e5c-d96c-417a-8bb0-f8b22a03c081', 'workreportteacher',
        '13a336b9-dff3-4e28-9a5a-b2fabd1425b2', '2022-10-09 17:39:26');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 09:25:59', '1bc4d536-a915-4d99-b3c6-6016f68f20d9.jpg', '4f7c6bc5ba311331ad98590802ecdade.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '00f79da3-1ba2-42e1-9f7e-9d6ddd40547e', 'travelreimbursement',
        '1bc4d536-a915-4d99-b3c6-6016f68f20d9', '2022-10-15 09:26:03');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 15:22:59', '33d232fa-d81a-4f62-aaa3-bd65a793af14.jpg', '0e87dcf54635fa233dda71da6136b40d.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '995ce0f1-4dd0-4f53-9a7c-46cff3a3e312', 'travelreimbursement',
        '33d232fa-d81a-4f62-aaa3-bd65a793af14', '2022-10-15 15:23:21');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-09 17:39:24', '54464d68-078e-446a-8459-dc3d09029d50.jpg', '4f7c6bc5ba311331ad98590802ecdade.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'f7743e5c-d96c-417a-8bb0-f8b22a03c081', 'workreportteacher',
        '54464d68-078e-446a-8459-dc3d09029d50', '2022-10-09 17:39:26');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 14:24:01', '58b3fca0-3afb-4827-972a-73fe7146aeb8.png', '0ea7d84bea69e433b39a7903460d2f6a.png',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '9075d560-073b-4fa6-8919-6ca95b8fe3a1', 'travelreimbursement',
        '58b3fca0-3afb-4827-972a-73fe7146aeb8', '2022-10-15 14:24:05');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 15:20:31', '6457de6c-751b-4bc9-8dc9-6d6b4bf65a4c.jpg', '4e2b7ba0015f9486cb8d48e29feeb1c6.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'd9dda873-dafb-4e83-a5b0-e9c45bf73f6f', 'travelreimbursement',
        '6457de6c-751b-4bc9-8dc9-6d6b4bf65a4c', '2022-10-15 15:21:43');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 14:24:01', '8527cb97-eefa-4f19-a264-fe62cf6a7273.jpg', '1ce96ab9d1bc16ca890a898e690c69bc.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '9075d560-073b-4fa6-8919-6ca95b8fe3a1', 'travelreimbursement',
        '8527cb97-eefa-4f19-a264-fe62cf6a7273', '2022-10-15 14:24:05');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 09:25:59', '8c0607d7-f715-45b5-93ad-012aeafaa24b.jpg', '4c73b8bb0d973c81baf0c5e570b76197.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '00f79da3-1ba2-42e1-9f7e-9d6ddd40547e', 'travelreimbursement',
        '8c0607d7-f715-45b5-93ad-012aeafaa24b', '2022-10-15 09:26:03');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-09 17:39:23', '970fe3a6-f9bf-426e-ab27-870ab25a959b.jpg', '4c73b8bb0d973c81baf0c5e570b76197.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'f7743e5c-d96c-417a-8bb0-f8b22a03c081', 'workreportteacher',
        '970fe3a6-f9bf-426e-ab27-870ab25a959b', '2022-10-09 17:39:26');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 14:24:50', 'a9bdbd6e-8442-4dca-aa90-3d5e92a927cf.jpg', '4e2b7ba0015f9486cb8d48e29feeb1c6.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'd9dda873-dafb-4e83-a5b0-e9c45bf73f6f', 'travelreimbursement',
        'a9bdbd6e-8442-4dca-aa90-3d5e92a927cf', '2022-10-15 14:27:46');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 14:24:00', 'ab6f7678-b04f-4123-a5fc-c896b608fe71.jpg', '0e87dcf54635fa233dda71da6136b40d.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '9075d560-073b-4fa6-8919-6ca95b8fe3a1', 'travelreimbursement',
        'ab6f7678-b04f-4123-a5fc-c896b608fe71', '2022-10-15 14:24:05');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 09:25:59', 'b6b44062-69dc-49cb-bc77-8186507be5d7.jpg', '4e2b7ba0015f9486cb8d48e29feeb1c6.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '00f79da3-1ba2-42e1-9f7e-9d6ddd40547e', 'travelreimbursement',
        'b6b44062-69dc-49cb-bc77-8186507be5d7', '2022-10-15 09:26:03');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-21 08:31:17', 'bff30f69-264e-4008-8927-0fd2f0bcd3fc.jpg', '0e87dcf54635fa233dda71da6136b40d.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'cf251b59-765e-4b6f-a393-8e28ba12ac8d', 'changedepartmentbyteacher',
        'bff30f69-264e-4008-8927-0fd2f0bcd3fc', '2022-10-21 08:31:19');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-13 20:22:19', 'e238fa6c-9029-445a-a8e4-0e4f2302e1e6.jpg', '00c1f2c9e6dbabbe0fc7a8f5ce4b9fe9.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '3f0501ef-35ed-4372-9b69-f20ad2ca0b7d', 'travelreimbursement',
        'e238fa6c-9029-445a-a8e4-0e4f2302e1e6', '2022-10-13 20:22:21');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-13 20:24:07', 'ea694d54-554f-4096-ae0f-c8ca0ad200c3.jpg', '0e87dcf54635fa233dda71da6136b40d.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '00f79da3-1ba2-42e1-9f7e-9d6ddd40547e', 'travelreimbursement',
        'ea694d54-554f-4096-ae0f-c8ca0ad200c3', '2022-10-13 20:29:37');
INSERT INTO `filename` (`create_time`, `FileName`, `oldFileName`, `ReleaseUid`, `RowUid`, `TableUid`, `uid`,
                        `update_time`)
VALUES ('2022-10-15 13:50:42', 'f0e29533-a92c-42d8-8e3f-0a7ec7846974.jpg', '5e66630daee334f0e9c2a967cd67f093.jpg',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', 'aca06ede-9c62-4d42-9653-fd197ca30a05', 'travelreimbursement',
        'f0e29533-a92c-42d8-8e3f-0a7ec7846974', '2022-10-15 13:51:03');

INSERT INTO `leader` (`create_time`, `email`, `gender`, `password`, `realeName`, `status`, `tel`, `uid`, `update_time`,
                      `username`)
VALUES ('2022-10-02 19:41:51', '20021816@163.com', '男', '12345678', '英朗', '0', '13974813324',
        '26836185-2caa-4036-9b85-2b820ace9b2d', '2022-10-02 19:41:51', 'YMDXCC');
INSERT INTO `leader` (`create_time`, `email`, `gender`, `password`, `realeName`, `status`, `tel`, `uid`, `update_time`,
                      `username`)
VALUES ('2022-09-14 20:15:06', '123@qq.com', '男', 'adminadmin', 'test1', '0', '15898764567',
        '37c1647c-0c0f-465f-977d-3e3f027a7ad3', '2022-09-14 20:15:06', 'test1');
INSERT INTO `leader` (`create_time`, `email`, `gender`, `password`, `realeName`, `status`, `tel`, `uid`, `update_time`,
                      `username`)
VALUES ('2022-09-14 20:15:44', '123@qq.com', '男', 'adminadmin', 'test2', '0', '15845677654',
        'c1e7b1ab-4c0c-489f-a8d7-c76317436b75', '2022-09-14 20:15:44', 'test2');
INSERT INTO `leader` (`create_time`, `email`, `gender`, `password`, `realeName`, `status`, `tel`, `uid`, `update_time`,
                      `username`)
VALUES ('2022-09-14 20:16:32', 'asd@163.com', '女', 'adminadmin', 'test3', '0', '13987655678',
        'c340cd4a-df60-4e19-bf1b-a990f505bbe6', '2022-09-14 20:16:32', 'test3');

INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:39', '2022-10-10 16:41:36', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '2333', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:36', '0', '0f2671c9-59b4-4acc-94ab-0129f715021a',
        '2022-10-09 16:41:39');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-08 19:29:21', '2022-10-09 19:29:17', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试测试', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-08 19:29:17', '0', '1f4f023c-e875-4745-a742-ad65fe8650ed',
        '2022-10-08 19:29:21');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:44', '2022-10-10 16:41:42', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '444', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:42', '0', '26aadcbe-5b50-4dfe-b8e2-3b442faf257c',
        '2022-10-09 16:41:44');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:24', '2022-10-10 16:41:22', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:22', '0', '365a6d54-3dcd-4872-82f2-e5a202679270',
        '2022-10-09 16:41:24');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:48', '2022-10-10 16:41:45', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '555', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:45', '0', '426df581-0f0b-4a79-82f3-29b9d2a42551',
        '2022-10-09 16:41:48');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:53', '2022-10-10 16:41:52', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '777', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:52', '0', '57fac5e7-84c3-4c54-aebf-a46b2cf6fc4c',
        '2022-10-09 16:41:53');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:51', '2022-10-10 16:41:49', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '666', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:49', '0', '655ea509-03e0-4c04-9b3e-6ec0a74eec50',
        '2022-10-09 16:41:51');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:30', '2022-10-10 16:41:29', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '2', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:29', '0', '69839840-308e-4dab-a6a4-a69be2279596',
        '2022-10-09 16:41:30');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:21', '2022-10-10 16:41:16', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:16', '0', '6bbfbef1-2200-4a6b-a96f-3fd6e5b3c738',
        '2022-10-09 16:41:21');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:36', '2022-10-10 16:41:34', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '222', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:34', '0', '6c079122-5fe5-45cb-ba9b-58497d446b81',
        '2022-10-09 16:41:36');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:28', '2022-10-10 16:41:25', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '1', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:25', '0', 'a2b89cab-9409-4988-8674-348e01fb21ab',
        '2022-10-09 16:41:28');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:33', '2022-10-10 16:41:31', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '111', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:31', '0', 'a6bcc97b-d458-4f46-b04e-617fa2001845',
        '2022-10-09 16:41:33');
INSERT INTO `leave` (`count`, `create_time`, `end_time`, `nextUid`, `reason`, `reject_reason`, `releaseUid`,
                     `start_time`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:41:42', '2022-10-10 16:41:40', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '333', null,
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-10-09 16:41:40', '0', 'd74cc77b-bd59-4444-9072-ffb6dbcb9b63',
        '2022-10-09 16:41:42');



INSERT INTO `process` (`create_time`, `name`, `process`, `uid`, `update_time`)
VALUES ('2022-10-13 19:29:21', '差旅报销申请',
        'nowDepartment||c340cd4a-df60-4e19-bf1b-a990f505bbe6||c1e7b1ab-4c0c-489f-a8d7-c76317436b75',
        '4d4b1437-acaa-4eb5-a1ab-9cad78564fed', '2022-10-13 19:29:26');
INSERT INTO `process` (`create_time`, `name`, `process`, `uid`, `update_time`)
VALUES ('2022-10-05 14:27:19', '教师工作报告提交', 'nowDepartment', '5c4a2ce8-6c37-402b-bff3-998da0cc3376',
        '2022-10-05 14:27:19');
INSERT INTO `process` (`create_time`, `name`, `process`, `uid`, `update_time`)
VALUES ('2022-10-07 18:32:51', '请假申请', 'nowDepartment||c1e7b1ab-4c0c-489f-a8d7-c76317436b75',
        '64c0e0cc-8c3b-40de-aff3-3d293b6ac868', '2022-10-07 18:32:51');
INSERT INTO `process` (`create_time`, `name`, `process`, `uid`, `update_time`)
VALUES ('2022-09-28 18:33:53', '教师部门变更申请',
        'nowDepartment||nextDepartment||37c1647c-0c0f-465f-977d-3e3f027a7ad3', 'ab0c4b48-0651-4f22-ba23-4587ae502e89',
        '2022-10-13 19:30:48');


INSERT INTO `teacher` (`create_time`, `departmentUid`, `email`, `gender`, `password`, `realeName`, `status`, `tel`,
                       `uid`, `update_time`, `username`)
VALUES ('2022-09-14 20:20:52', '660a58b3-1b68-4348-beb5-5df5e9e5f9ea', 'ww@163.com', '男', 'adminadmin', '王五', '0',
        '18967899876', 'b436bc05-37ec-49ed-b374-2e84b683b380', '2022-09-14 20:20:52', 'ww');
INSERT INTO `teacher` (`create_time`, `departmentUid`, `email`, `gender`, `password`, `realeName`, `status`, `tel`,
                       `uid`, `update_time`, `username`)
VALUES ('2022-09-14 20:19:50', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'zs@qq.com', '男', 'adminadmin', '张三', '0',
        '15834566543', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '2022-09-14 20:19:50', 'zs');
INSERT INTO `teacher` (`create_time`, `departmentUid`, `email`, `gender`, `password`, `realeName`, `status`, `tel`,
                       `uid`, `update_time`, `username`)
VALUES ('2022-09-14 20:20:17', '285040e7-dbe9-4666-90c0-70b8d0af84fb', 'ls@outlook.com', '男', 'adminadmin', '李四',
        '0', '15856781234', 'ed4160ed-59d6-4c59-b17f-5584a920135c', '2022-09-14 20:20:17', 'ls');
INSERT INTO `teacher` (`create_time`, `departmentUid`, `email`, `gender`, `password`, `realeName`, `status`, `tel`,
                       `uid`, `update_time`, `username`)
VALUES ('2022-10-02 19:45:11', '285040e7-dbe9-4666-90c0-70b8d0af84fb', 'woqhdm@163.com', '男', '123456789', '魏武', '0',
        '17758456134', 'f4f5ccee-058b-4111-bd45-ac8cf85c2104', '2022-10-02 19:45:11', '123456789');

INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-13 20:29:37', '湖南长沙', '1211CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试1211',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '00f79da3-1ba2-42e1-9f7e-9d6ddd40547e', '2022-10-15 09:26:34');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-13 20:06:47', '湖南长沙', '1000.78CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试原因',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '066482ab-ec86-459d-8d9b-de1877a16629', '2022-10-13 20:23:31');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-19 19:34:41', '12', '12CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '0', '0fe5bf8e-3418-49a6-9733-e79cf005063a', '2022-10-19 19:34:41');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-13 20:22:21', '湖南长沙', '1212USD', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试原因',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '3f0501ef-35ed-4372-9b69-f20ad2ca0b7d', '2022-10-13 20:23:19');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-13 20:14:19', '湖南长沙', '192USD', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试原因',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '47b0f1b5-1bbf-4a0e-985b-0b6a31681082', '2022-10-13 20:23:31');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 09:32:07', '北京', '300USD', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试原因123',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '7eb9e6bb-f959-4b22-b427-805fdae5912d', '2022-10-15 09:42:58');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 14:24:05', '12', '12CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '9075d560-073b-4fa6-8919-6ca95b8fe3a1', '2022-10-15 14:27:18');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 15:23:21', '12', '12CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', '995ce0f1-4dd0-4f53-9a7c-46cff3a3e312', '2022-10-19 16:56:36');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 13:50:32', 'test', '100CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '1212',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', 'aca06ede-9c62-4d42-9653-fd197ca30a05', '2022-10-15 13:51:23');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 09:43:18', '湖南省长沙市', '100USD', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '测试原因',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', 'c73340bf-15bf-4863-963e-a01591b63558', '2022-10-19 16:56:36');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-13 19:46:14', '122', '111USD', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '111111',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', 'cb3d4c86-6ec7-4fd8-b8aa-94f9c44c4f12', '2022-10-13 20:06:04');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 14:27:46', '12', '12CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', 'd9dda873-dafb-4e83-a5b0-e9c45bf73f6f', '2022-10-19 16:56:35');
INSERT INTO `travelreimbursement` (`count`, `create_time`, `destination`, `expenses`, `nextUid`, `reason`, `releaseUid`,
                                   `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-15 14:18:44', '12', '12CNY', 'b752d7a0-8373-4c4c-864f-a85d9999f433', '12',
        'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '-1', 'de8fb07f-ec3a-434b-9965-cc16d52775fe', '2022-10-19 16:56:36');


INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 19:16:53', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '038ffde6-a8b8-4d3f-b97d-3d1146265586', '2022-10-05 19:17:11');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 16:47:32', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '23d3428c-93b3-4d24-8ee0-a6133b6e9c0d', '2022-10-05 17:36:21');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 20:24:46', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '2adbe560-62df-49ba-bfe4-c7596b9061be', '2022-10-05 20:24:57');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 17:39:19', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '3b640113-95d0-4a88-8150-cf8e1dd34461', '2022-10-05 17:39:28');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 17:46:24', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '3c5bd6ac-2c67-478f-be72-79cb71462b80', '2022-10-05 17:46:28');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 17:47:25', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '72ee1f5a-2b05-41c5-9f16-d10b8f702dd4', '2022-10-05 19:08:30');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-06 19:56:32', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '7e95f38d-0034-4b3d-8a2e-9648bc58fabc', '2022-10-06 19:56:37');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 19:22:45', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', '9d773714-b1ab-44be-a41e-ce2e5f75c8a1', '2022-10-05 19:22:56');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 22:36:59', '285040e7-dbe9-4666-90c0-70b8d0af84fb', 'f4f5ccee-058b-4111-bd45-ac8cf85c2104',
        '-1', 'a67743a9-8cc6-4844-bc3f-f3e9b9a6c587', '2022-10-05 22:37:09');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 16:06:11', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', 'a85ebca1-d85e-48b3-b6c5-80d9da45dfd2', '2022-10-09 17:39:17');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 19:27:32', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', 'c219510b-bcc1-4d47-874d-ab70ce7f3a6d', '2022-10-05 19:27:45');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 17:45:25', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', 'c4b94190-7f37-490b-a58b-2f9a8e0a636d', '2022-10-05 17:45:29');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-05 19:24:34', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb',
        '-1', 'd7486866-5d9c-4b77-b239-28b703afcc0d', '2022-10-05 19:24:45');
INSERT INTO `workreportteacher` (`count`, `create_time`, `nextUid`, `releaseUid`, `status`, `uid`, `update_time`)
VALUES ('0', '2022-10-09 17:39:26', 'b752d7a0-8373-4c4c-864f-a85d9999f433', 'e35a9b06-3506-48e4-8f3c-4cfbfe7e8cdb', '0',
        'f7743e5c-d96c-417a-8bb0-f8b22a03c081', '2022-10-09 17:39:26');

