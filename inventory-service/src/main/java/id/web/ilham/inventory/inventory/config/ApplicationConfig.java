package id.web.ilham.inventory.inventory.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final HttpHostsConfiguration httpHostsConfiguration;

    @Bean
    public PoolingHttpClientConnectionManager defaultPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpHostsConfiguration.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpHostsConfiguration.getDefaultMaxPerRoute());

        if (CollectionUtils.isNotEmpty(httpHostsConfiguration.getMaxPerRoutes())) {
            for (HttpHostsConfiguration.HttpHostConfiguration httpHostConfig : httpHostsConfiguration.getMaxPerRoutes()) {
                HttpHost host = new HttpHost(httpHostConfig.getScheme(), httpHostConfig.getHost(), httpHostConfig.getPort());
                // Max per route for a specific host route
                connectionManager.setMaxPerRoute(new HttpRoute(host), httpHostConfig.getMaxPerRoute());
            }
        }

        return connectionManager;
    }
}
