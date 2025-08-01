package com.bank.publicinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableJpaRepositories("com.bank.publicinfo.repositories")
@EntityScan( "com.bank.publicinfo.entities")
public class PublicInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublicInfoApplication.class, args);
    }
}
