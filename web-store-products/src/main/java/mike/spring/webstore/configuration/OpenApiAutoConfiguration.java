package mike.spring.webstore.configuration;

import java.util.Optional;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import jakarta.validation.constraints.NotNull;

@Configuration
class OpenApiAutoConfiguration {

    @Bean
    public OpenApiCustomizer openApiCustomizer(@NotNull BuildProperties buildProperties) {
        return openapi -> {
            
            openapi.getInfo().version(buildProperties.getVersion());

            Optional.ofNullable(buildProperties.get("app.contact.email"))
                .ifPresent(email -> Optional.ofNullable(openapi.getInfo().getContact())
                    .ifPresentOrElse(contact -> contact.setEmail(email), () -> 
                        openapi.getInfo().setContact(new Contact().email(email))
                    ));

            Optional.ofNullable(buildProperties.getTime())
                .ifPresent(instant -> {
                    var description = openapi.getInfo().getDescription();
                    openapi.getInfo().setDescription(String.format("%s (build: %s)", description, instant));
                });
        };
    }
}
