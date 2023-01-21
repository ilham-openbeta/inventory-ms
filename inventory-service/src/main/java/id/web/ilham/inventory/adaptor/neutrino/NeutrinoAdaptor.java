package id.web.ilham.inventory.adaptor.neutrino;

import id.web.ilham.inventory.model.NeutrinoRequest;
import id.web.ilham.inventory.model.NeutrinoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NeutrinoAdaptor {

    private final RestTemplate neutrinoRestTemplate;

    @Value("${neutrino.url}")
    private String url;

    public NeutrinoResponse convertUnit(NeutrinoRequest neutrinoRequest) {
        return neutrinoRestTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(neutrinoRequest),
                NeutrinoResponse.class
        ).getBody();
    }
}
