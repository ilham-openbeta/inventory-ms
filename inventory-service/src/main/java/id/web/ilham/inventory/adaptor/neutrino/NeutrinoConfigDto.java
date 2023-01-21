package id.web.ilham.inventory.adaptor.neutrino;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "neutrino")
public class NeutrinoConfigDto {

    private Integer connectionRequestTimeout;

    private Integer connectTimeout;

    private Integer readTimeout;

    private String userId;

    private String apiKey;
}
