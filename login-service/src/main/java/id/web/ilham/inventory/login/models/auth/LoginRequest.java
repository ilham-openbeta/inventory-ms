package id.web.ilham.inventory.login.models.auth;


import id.web.ilham.inventory.common.model.CommonRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest implements CommonRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
