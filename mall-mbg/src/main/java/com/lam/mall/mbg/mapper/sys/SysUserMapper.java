package com.lam.mall.mbg.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lam.mall.mbg.model.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT * FROM sys_user WHERE username=#{username}")
    SysUser selectByUserName(@Param("username")String username);

    @Select("SELECT * FROM sys_user WHERE union_id=#{openId} or mp_open_id=#{openId} or mini_open_id=#{openId}")
    SysUser selectByOpenId(@Param("openId")String openId);
}
