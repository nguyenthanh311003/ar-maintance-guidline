package com.capstone.ar_guideline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArGuidelineApplication {

  public static void main(String[] args) {
    SpringApplication.run(ArGuidelineApplication.class, args);
  }
}
