package br.com.deliberation_api;

import br.com.deliberation_api.infrastructure.config.ApiProperties;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableMongock
@EnableConfigurationProperties(ApiProperties.class)
public class DeliberationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliberationApiApplication.class, args);
	}

}
