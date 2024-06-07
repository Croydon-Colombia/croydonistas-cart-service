package com.croydon;

import jakarta.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CroydonistasCartServiceApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.systemDefault().toString()));
    }

    public static void main(String[] args) {
        SpringApplication.run(CroydonistasCartServiceApplication.class, args);
    }

}
