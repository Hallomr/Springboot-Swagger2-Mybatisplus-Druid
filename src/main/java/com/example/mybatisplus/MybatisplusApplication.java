package com.example.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.example.mybatisplus"})
@MapperScan("com.example.mybatisplus.mapper")
@ComponentScan("com.example.mybatisplus")
public class MybatisplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
    }

    /*
     *解决日期入参问题
     * */
    /*@Bean
    public ObjectMapper serializingObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        return objectMapper;

    }*/
}
