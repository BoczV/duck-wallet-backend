package com.duckwallet.userservice;

import com.duckwallet.userservice.model.AppUser;
import com.duckwallet.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class UserServiceApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/user/**")).build();
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
			AppUser user = AppUser.builder().nickName("Isti").firstName("István").lastName("Trautmann").password("ugyi").email("isti@gmail.com").build();
			userRepository.save(user);
        };
    }
}
