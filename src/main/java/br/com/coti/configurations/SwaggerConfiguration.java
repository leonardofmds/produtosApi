package br.com.coti.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

	@Bean
	OpenAPI customOpenApi() {
		var openApi = new OpenAPI().components(new Components())
				.info(new Info().title("Produtos API").version("1.0.0")
						.description("API Spring Boot para controle de produtos")
						.contact(new Contact().name("COTI Informática")));

		return openApi;
	}
}
