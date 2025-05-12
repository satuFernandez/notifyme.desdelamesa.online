package desdelamesa.online.notifyme.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(name = "environment", havingValue = "LOCAL")
public class CorsConfigLocal implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {

		registry.addMapping("/**").allowedOrigins("http://localhost:63343").allowedMethods("*").allowedHeaders("*")
				.allowCredentials(true);
	}
}