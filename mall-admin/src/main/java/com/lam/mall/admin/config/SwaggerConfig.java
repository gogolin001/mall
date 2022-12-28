package com.lam.mall.admin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 *
 **/
@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";
    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "com.lam.mall.admin.controller" };
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
                        .title("mall商城后台系统API")
                        .version("1.0.0")
                        .description( "B2B2C商城")
                        .termsOfService("https://www.mall.com")
                        .license(new License().name("Apache 2.0").url("https://www.mall.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("mall商城开发第一个版本")
                        .url("http://www.mall.com"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("Bearer ")
                                        .bearerFormat("JWT")));
    }
}
