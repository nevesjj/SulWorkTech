package com.MV.DesafioSulWorkTech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DesafioSulWorkTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioSulWorkTechApplication.class, args);
	}

}
