package mike.spring.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Web-Store Products API",
                description = "Web-Store products service",
                version = "0.0.1"))
public class WebStoreProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebStoreProductApplication.class, args);
    }
}