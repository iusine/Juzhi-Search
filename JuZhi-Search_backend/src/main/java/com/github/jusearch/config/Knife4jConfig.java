package com.github.jusearch.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j整合Swagger3 Api接口文档配置类
 *
 * @author iusie
 * @date 2024/11/15
 */
@Configuration
public class Knife4jConfig {

    /**
     * 配置基本信息
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Juzhi-Search  API")
                        // 接口文档描述
                        .description("Juzhi-Search")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("幽心").url("iusine@163.com")))
                .externalDocs(new ExternalDocumentation()
                        // 额外补充说明
                        .description("仓库地址")
                        // 额外补充链接
                        .url("https://github.com/iusine/Juzhi-Search"));
    }

}
