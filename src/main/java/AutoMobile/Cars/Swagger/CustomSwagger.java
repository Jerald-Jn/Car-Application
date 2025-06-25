package AutoMobile.Cars.Swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class CustomSwagger {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI(SpecVersion.V31).info(new Info().title("Car Application API").version("24.02")
                .description("Welcome to our API Swgger website").summary("Greeting")
                .contact(new Contact().email("jjerald2000@gmail.com").name("Jerald")));
    }
}
