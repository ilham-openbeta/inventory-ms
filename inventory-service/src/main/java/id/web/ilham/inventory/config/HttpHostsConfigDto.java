package id.web.ilham.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "http-conn-pool")
public class HttpHostsConfigDto {

    private Integer maxTotal;
    private Integer defaultMaxPerRoute;
    private List<HttpHostConfiguration> maxPerRoutes;

    @Data
    public static class HttpHostConfiguration {

        private String scheme;
        private String host;
        private Integer port;
        private Integer maxPerRoute;

    }
}