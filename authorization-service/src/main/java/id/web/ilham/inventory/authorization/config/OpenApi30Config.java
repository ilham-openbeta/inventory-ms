package id.web.ilham.inventory.authorization.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Inventory Authorization API",
        version = "v1",
        description = "This is a simple service for authorize inventory API. "))
public class OpenApi30Config {

}