package io.bootify.my_app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MyAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MyAppApplication.class, args);

        System.err.println("PORT : localhost8092");
        System.err.println("Swagger documentation : "+"http://localhost:8092/swagger-ui/index.html#/");
    }

}
