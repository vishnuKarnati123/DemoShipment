package com.exam.shipement;

import org.modelmapper.ModelMapper;import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;






@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info =@Info(title="Shipment API", description = "Shipment MicroService") )
public class DemoShipementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoShipementApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
