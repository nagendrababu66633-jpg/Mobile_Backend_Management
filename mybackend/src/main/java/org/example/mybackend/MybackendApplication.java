
package org.example.mybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.mybackend", "controller", "service"})
public class MybackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybackendApplication.class, args);
    }
}