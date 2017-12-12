package com.tung7.ex.repository.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/2/25.
 * @update
 */
@SpringBootApplication
//@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return new CommandLineRunner(){
            @Override
            public void run(String... strings) throws Exception {
                System.out.println("=====================start===========================");
                System.out.println("Let's inspect the beans provided by Spring Boot: 1 ");
                System.out.println("Let's inspect the beans provided by Spring Boot: 2 ");
                System.out.println("Let's inspect the beans provided by Spring Boot: 3 ");
                System.out.println("=====================end===========================");
            }
        };
    }
}
