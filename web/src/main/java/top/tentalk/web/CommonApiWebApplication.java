package top.tentalk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"top.tentalk.auth","top.tentalk.business"})
@ServletComponentScan(basePackages = {"top.tentalk.auth"})
public class CommonApiWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonApiWebApplication.class, args);
    }
}