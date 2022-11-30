package com.lam.mall.admin.config;

/*import com.lam.mall.common.config.BaseSwaggerConfig;
import com.lam.mall.common.domain.SwaggerProperties;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 *
 **/
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "com.lam.mall.admin" };
        return GroupedOpenApi.builder().group("用户模块")
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> {
                    return operation.addParametersItem(new HeaderParameter().name("groupCode").example("测试").description("集团code").schema(new StringSchema()._default("BR").name("groupCode").description("集团code")));
                })
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XXX用户系统API")
                        .version("1.0")
                        .description( "Knife4j集成springdoc-openapi示例")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }


}
