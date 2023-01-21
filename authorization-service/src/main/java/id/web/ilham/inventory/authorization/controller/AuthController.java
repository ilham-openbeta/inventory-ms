package id.web.ilham.inventory.authorization.controller;

import id.web.ilham.inventory.authorization.model.auth.JwtResponse;
import id.web.ilham.inventory.authorization.model.auth.LoginRequest;
import id.web.ilham.inventory.authorization.model.auth.SignupRequest;
import id.web.ilham.inventory.authorization.service.GetTokenJWKSService;
import id.web.ilham.inventory.authorization.service.PostAuthLoginService;
import id.web.ilham.inventory.authorization.service.PostAuthRegisterService;
import id.web.ilham.inventory.common.model.EmptyResponse;
import id.web.ilham.inventory.common.model.ResponseMessage;
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

    private final PostAuthRegisterService postAuthRegisterService;

    private final PostAuthLoginService postAuthLoginService;

    private final GetTokenJWKSService getTokenJwksService;

    @Operation(summary = "Request token", description = "Request token with username and password", tags = {"authentication"})
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseMessage<JwtResponse> postAuthLogin(@Valid @RequestBody LoginRequest loginRequest) {
        return postAuthLoginService.execute(loginRequest);
    }

    @Operation(
            summary = "Create a new user account",
            description = "Create a new user account to give data editing access from API.",
            tags = {"authentication"})
    @PostMapping(value = "/register", produces = "application/json")
    public ResponseMessage<EmptyResponse> postAuthRegister(@Valid @RequestBody SignupRequest signUpRequest) {
        return postAuthRegisterService.execute(signUpRequest);
    }

    @GetMapping("/token/jwks.json")
    public Map<String, Object> getTokenJWKS() {
        return getTokenJwksService.getKey();
    }
}