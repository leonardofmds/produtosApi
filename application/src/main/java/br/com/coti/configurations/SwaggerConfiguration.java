package br.com.coti.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {

	/**
	 * Método para configurar o Swagger
	 * @return OpenAPI
	 */
	@Bean
	OpenAPI customOpenApi() {

		return new OpenAPI().components(new Components())
				.info(new Info().title("Produtos API").version("1.0.0")
						.description("API Spring Boot para controle de produtos")
						.contact(new Contact().name("COTI Informática")));
	}
}
