package com.tech.sulwork;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Café da Manhã", version = "1.0", description = "Documentação da API para o sistema de café colaborativo"))
public class SulworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SulworkApplication.class, args);
	}

}
