package id.web.ilham.inventory.inventory.adaptor;

import id.web.ilham.inventory.inventory.model.NeutrinoRequest;
import id.web.ilham.inventory.inventory.model.NeutrinoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static id.web.ilham.inventory.inventory.contant.Constant.API_KEY;
import static id.web.ilham.inventory.inventory.contant.Constant.USER_ID;

@Component
@RequiredArgsConstructor
public class NeutrinoAdaptor {

    private final RestTemplate defaultRestTemplate;

    @Value("${neutrino.url}")
    private String url;
    @Value("${neutrino.userId}")
    private String userId;
    @Value("${neutrino.apiKey}")
    private String apiKey;

    public NeutrinoResponse convertUnit(NeutrinoRequest neutrinoRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(USER_ID, userId);
        httpHeaders.set(API_KEY, apiKey);

        return defaultRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(neutrinoRequest, httpHeaders),
                NeutrinoResponse.class
        ).getBody();
    }
}
