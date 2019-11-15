package com.example.mybatisplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //生成被restcontroller注解的接口
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                //生成所有api接口
                .apis(RequestHandlerSelectors.basePackage("com.example.mybatisplus.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    //配置在线文档的基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot整合swagger构建api文档")
                .description("API详细文档")
                .termsOfServiceUrl("")
                // .contact(new Contact("ONE基础平台","http://192.168.15.246:8025/#/login","scsoft@163.com"))
                .version("1.0")
                .build();
    }

    /**
     * 添加静态资源--过滤swagger-api (开源的在线API文档)
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //过滤swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        /*registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
*/
       /* registry.addResourceHandler("/swagger/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger*");
*//*
        registry.addResourceHandler("/v2/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/v2/api-docs/");*/
    }
}
