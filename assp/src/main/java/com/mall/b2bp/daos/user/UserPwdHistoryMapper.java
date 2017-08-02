package com.mall.b2bp.daos.user;

import com.mall.b2bp.models.user.UserPwdHistory;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


public interface UserPwdHistoryMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UserPwdHistory record);

    int insertSelective(UserPwdHistory record);

    UserPwdHistory selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UserPwdHistory record);

    int updateByPrimaryKey(UserPwdHistory record);

    int selectByPwdHistory(@Param("userId") BigDecimal userId,@Param("pwd") String pwd, @Param("times") int times);
    BigDecimal selectNextId();
}