package id.web.ilham.inventory.authorization.model.auth;

import id.web.ilham.inventory.common.model.CommonResponse;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse implements CommonResponse {
    private final List<String> roles;
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String email;

    public JwtResponse(String accessToken, Integer id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
