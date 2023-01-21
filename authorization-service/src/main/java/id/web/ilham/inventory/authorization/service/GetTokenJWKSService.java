package id.web.ilham.inventory.authorization.service;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import id.web.ilham.inventory.authorization.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetTokenJWKSService {

    private final JwtUtil jwtUtil;

    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) jwtUtil.getKeyPair().getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
