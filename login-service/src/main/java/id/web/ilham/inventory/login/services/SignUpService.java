package id.web.ilham.inventory.login.services;

import id.web.ilham.inventory.common.exception.ApplicationException;
import id.web.ilham.inventory.common.model.EmptyResponse;
import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.common.service.CommonService;
import id.web.ilham.inventory.login.entities.Role;
import id.web.ilham.inventory.login.entities.User;
import id.web.ilham.inventory.login.entities.UserRoles;
import id.web.ilham.inventory.login.models.auth.SignupRequest;
import id.web.ilham.inventory.login.repositories.RoleRepository;
import id.web.ilham.inventory.login.repositories.UserRepository;
import id.web.ilham.inventory.login.repositories.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService implements CommonService<SignupRequest, EmptyResponse> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRolesRepository UserRolesRepository;

    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public ResponseMessage<EmptyResponse> execute(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "error.username");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "error.email");
        }

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "error.role"));

        userRepository.save(user);
        UserRoles userRoles = UserRoles.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();

        UserRolesRepository.save(userRoles);

        return ResponseMessage.success(new EmptyResponse());
    }
}
