package id.web.ilham.inventory.inventory.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ApplicationConfig {

    @Value("${httpclient.pool.defaultMaxTotalConnections}")
    private Integer defaultMaxTotalConnections;

    @Value("${httpclient.pool.defaultMaxConnectionsPerRoute}")
    private Integer defaultMaxConnectionsPerRoute;

    @Value("${neutrino.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${neutrino.connectTimeout}")
    private Integer connectTimeout;

    @Value("${neutrino.readTimeout}")
    private Integer readTimeout;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(defaultMaxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(defaultMaxConnectionsPerRoute);
        return connectionManager;
    }

    @Bean
    public HttpClient defaultHttpClient() {
        return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager()).build();
    }

    @Bean
    public RestTemplate defaultRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(connectionRequestTimeout);

        return new RestTemplateBuilder()
                .requestFactory(() -> factory)
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .build();
    }
}
