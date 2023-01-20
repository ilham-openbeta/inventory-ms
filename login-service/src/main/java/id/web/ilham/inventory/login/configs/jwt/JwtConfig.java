package id.web.ilham.inventory.login.configs.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String publicKey;
    private String privateKey;
    private Integer expirationMs;
    private String issuer;
}
