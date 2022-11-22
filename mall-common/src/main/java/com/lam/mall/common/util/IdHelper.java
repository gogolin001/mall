package com.lam.mall.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;

/**
 * 描述: ID生成
 * 版权: Copyright (c) 2022
 * 公司: XXX
 * 作者: lam
 * 版本: 1.0
 * 创建日期: 2020/9/16 9:59 */
public class IdHelper {

    @Value("${mybatis-plus.global-config.worker-id}")
    private static Long workerId;
    @Value("${mybatis-plus.global-config.datacenter-id}")
    private static Long dataCenterId;

    //用mybatis-plus生成Long ID
    public static Long getNextId()
    {
        IdentifierGenerator identifierGenerator = ObjectUtil.isNull(workerId) || ObjectUtil.isNull(dataCenterId) ? new DefaultIdentifierGenerator():new DefaultIdentifierGenerator(workerId,dataCenterId);
        Number id=  identifierGenerator.nextId(new Object());
        Long nextId = Long.parseLong(id+"");
        return nextId;
    }
}
