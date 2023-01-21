package id.web.ilham.inventory.authorization.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfigDto {
    private String publicKey;
    private String privateKey;
    private Integer expirationMs;
    private String issuer;
}
