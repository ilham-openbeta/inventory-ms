package id.web.ilham.inventory.login.controllers;

import id.web.ilham.inventory.common.model.EmptyResponse;
import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.login.models.auth.JwtResponse;
import id.web.ilham.inventory.login.models.auth.LoginRequest;
import id.web.ilham.inventory.login.models.auth.SignupRequest;
import id.web.ilham.inventory.login.services.JWKSService;
import id.web.ilham.inventory.login.services.SignInService;
import id.web.ilham.inventory.login.services.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpService signUpService;

    private final SignInService signInService;

    private final JWKSService jwksService;

    @Operation(summary = "Request token", description = "Request token with username and password", tags = {"authentication"})
    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseMessage<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return signInService.execute(loginRequest);
    }

    @Operation(
            summary = "Create a new user account",
            description = "Create a new user account to give data editing access from API.",
            tags = {"authentication"})
    @PostMapping(value = "/signup", produces = "application/json")
    public ResponseMessage<EmptyResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return signUpService.execute(signUpRequest);
    }

    @GetMapping("/token/jwks.json")
    public Map<String, Object> getKey() {
        return jwksService.getKey();
    }
}