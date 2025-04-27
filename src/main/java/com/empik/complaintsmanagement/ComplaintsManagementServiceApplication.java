package com.empik.complaintsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.empik.complaintsmanagement")
public class ComplaintsManagementServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ComplaintsManagementServiceApplication.class, args);
  }
}
