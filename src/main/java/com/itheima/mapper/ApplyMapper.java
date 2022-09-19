package com.itheima.mapper;

import org.apache.ibatis.annotations.Param;

public interface ApplyMapper {

    public int teacherChangeDepartment(@Param("uid") String uid, @Param("releaseUid") String releaseUid, @Param("departmentUid") String departmentUid, @Param("changeReason") String changeReason, @Param("changeFile") String changeFile);

}
