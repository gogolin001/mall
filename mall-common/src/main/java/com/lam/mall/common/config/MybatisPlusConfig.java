package com.lam.mall.common.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Value("${mybatis-plus.global-config.worker-id}")
    private Long workerId;
    @Value("${mybatis-plus.global-config.datacenter-id}")
    private Long dataCenterId;
    /**
     * 乐观锁 插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 读取配置文件的workerId和dataCenterId配置雪花id生成
     * @return
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        if(ObjectUtil.isNull(workerId) || ObjectUtil.isNull(dataCenterId))
            return new DefaultIdentifierGenerator();
        return new DefaultIdentifierGenerator(workerId,dataCenterId);
    }
}
