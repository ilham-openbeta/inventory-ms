package id.web.ilham.inventory.authorization.config.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import id.web.ilham.inventory.authorization.model.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtConfigDto jwtConfigDto;

    private KeyPair keyPair;

    @PostConstruct
    protected void init() {
        keyPair = new KeyPair(getPublicKey(jwtConfigDto.getPublicKey()), getPrivateKey(jwtConfigDto.getPrivateKey()));
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JWSSigner signer;
        signer = new RSASSASigner(keyPair.getPrivate());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userPrincipal.getUsername())
                .issuer(jwtConfigDto.getIssuer())
                .issueTime(new Date())
                .expirationTime(new Date((new Date()).getTime() + jwtConfigDto.getExpirationMs()))
                .claim("roles", roles)
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).build(),
                claimsSet);

        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return signedJWT.serialize();
    }

    public String getUserNameFromJwtToken(String token) {
        String name;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            name = signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(authToken);
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) keyPair.getPublic());
            return signedJWT.verify(verifier);
        } catch (ParseException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public PublicKey getPublicKey(String pubKey) {
        PublicKey key;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(pubKey.getBytes(StandardCharsets.UTF_8));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public PrivateKey getPrivateKey(String privKey) {
        PrivateKey key;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privKey.getBytes(StandardCharsets.UTF_8));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}