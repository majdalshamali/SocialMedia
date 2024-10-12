package erp.mds.socialmediaback;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Social Media REST API Documentation",
				description = "Majd Alshamali REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Majd Alshamali",
						email = "majdalshamali86@gmail.com"
				),
				license = @License(
						name = "Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Social Media REST API Documentation",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
@EnableCaching
public class SocialMediaBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaBackApplication.class, args);
	}

}
