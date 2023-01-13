package id.web.ilham.inventory.login.services;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import id.web.ilham.inventory.login.configs.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JWKSService {

    private final JwtUtils jwtUtils;

    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) jwtUtils.getKeyPair().getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
