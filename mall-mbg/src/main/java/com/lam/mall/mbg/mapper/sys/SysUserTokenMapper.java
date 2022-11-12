package com.lam.mall.mbg.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lam.mall.mbg.model.sys.SysUserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserTokenMapper extends BaseMapper<SysUserToken> {
    @Select("SELECT * FROM sys_user_token WHERE username=#{username} and client_type=#{client}")
    SysUserToken SelectByUsernameAndClient(String username,String client);
}
