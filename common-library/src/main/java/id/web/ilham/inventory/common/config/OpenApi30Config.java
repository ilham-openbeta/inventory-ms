package id.web.ilham.inventory.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Inventory API",
        version = "v1",
        description = "This is a simple application for recording company inventory items. " +
                "Data can be accessed by anyone, but to change the data you first have to create an account and then " +
                "log in to get an authentication bearer token to change the data."))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApi30Config {

}