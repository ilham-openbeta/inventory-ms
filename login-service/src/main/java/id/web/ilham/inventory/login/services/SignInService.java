package id.web.ilham.inventory.login.services;

import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.common.service.CommonService;
import id.web.ilham.inventory.login.configs.jwt.JwtUtils;
import id.web.ilham.inventory.login.models.UserDetailsImpl;
import id.web.ilham.inventory.login.models.auth.JwtResponse;
import id.web.ilham.inventory.login.models.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignInService implements CommonService<LoginRequest, JwtResponse> {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseMessage<JwtResponse> execute(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseMessage.success(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}
