package com.mall.b2bp.daos.user;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.user.SysUserModel;
import com.mall.b2bp.vos.user.SysUserViewVo;

public interface SysUserModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(SysUserModel record);

    int insertSelective(SysUserModel record);

    SysUserModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(SysUserModel record);

    int updateByPrimaryKey(SysUserModel record);
    
    List<SysUserModel> selectListByVo (SysUserViewVo record);
}