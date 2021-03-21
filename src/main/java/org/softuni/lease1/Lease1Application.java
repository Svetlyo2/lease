package org.softuni.lease1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Lease1Application {

    public static void main(String[] args) {
        SpringApplication.run(Lease1Application.class, args);
    }

}
