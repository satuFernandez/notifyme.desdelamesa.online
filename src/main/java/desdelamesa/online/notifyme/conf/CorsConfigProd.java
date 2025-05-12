package desdelamesa.online.notifyme.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(name = "environment", havingValue = "PROD")
public class CorsConfigProd implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {

		registry.addMapping("/api/v1/notifyme/email")
				.allowedOrigins("https://desdelamesa.online", "https://www.desdelamesa.online").allowedMethods("POST")
				.allowedHeaders("Content-Type").allowCredentials(false).maxAge(2592000L);
	}
}