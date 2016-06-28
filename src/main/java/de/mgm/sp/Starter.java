package de.mgm.sp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@SpringBootApplication
@EnableConfigurationProperties
@EnableSwagger
public class Starter {

	@Autowired
	private SpringSwaggerConfig swaggerConfig;

	@Bean
	public SwaggerSpringMvcPlugin initSwagger() {
		return new SwaggerSpringMvcPlugin(swaggerConfig)
				// disable default API info
				.apiInfo(new ApiInfo(null, null, null, null, null, null))
				// exclude Spring default error handler
				.includePatterns("^((?!error).)*$");
	}

	public static void main(final String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
