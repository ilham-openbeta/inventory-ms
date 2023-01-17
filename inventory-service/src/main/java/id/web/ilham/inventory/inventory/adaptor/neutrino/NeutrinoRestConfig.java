package id.web.ilham.inventory.inventory.adaptor.neutrino;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static id.web.ilham.inventory.inventory.contant.Constant.API_KEY;
import static id.web.ilham.inventory.inventory.contant.Constant.USER_ID;

@Configuration
@RequiredArgsConstructor
public class NeutrinoRestConfig {

    private final NeutrinoConfiguration neutrinoConfiguration;
    private final PoolingHttpClientConnectionManager defaultPoolingHttpClientConnectionManager;

    @Bean
    public RestTemplate neutrinoRestTemplate() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(neutrinoConfiguration.getConnectionRequestTimeout()))
                .setConnectTimeout(Timeout.ofMilliseconds(neutrinoConfiguration.getConnectTimeout()))
                .setResponseTimeout(Timeout.ofMilliseconds(neutrinoConfiguration.getReadTimeout()))
                .build();

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(defaultPoolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // using this builder style can set additional interceptor & custom message converters
        return new RestTemplateBuilder()
                .requestFactory(() -> factory)
                .additionalInterceptors(new NeutrinoInterceptor())
                .build();
    }

    class NeutrinoInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().set(USER_ID, neutrinoConfiguration.getUserId());
            request.getHeaders().set(API_KEY, neutrinoConfiguration.getApiKey());

            return execution.execute(request, body);
        }
    }
}
