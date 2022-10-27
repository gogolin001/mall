package com.lam.mall.mbg.maper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lam.mall.mbg.model.SysAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface SysAuthorityMapper  extends BaseMapper<SysAuthority> {

    @Select("SELECT * FROM sys_authority")
    List<SysAuthority> list();
}
