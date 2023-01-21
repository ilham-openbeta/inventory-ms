package id.web.ilham.inventory.authorization.service;

import id.web.ilham.inventory.authorization.entity.Role;
import id.web.ilham.inventory.authorization.entity.User;
import id.web.ilham.inventory.authorization.entity.UserRole;
import id.web.ilham.inventory.authorization.model.auth.SignupRequest;
import id.web.ilham.inventory.authorization.repository.RoleRepository;
import id.web.ilham.inventory.authorization.repository.UserRepository;
import id.web.ilham.inventory.authorization.repository.UserRoleRepository;
import id.web.ilham.inventory.common.exception.ApplicationException;
import id.web.ilham.inventory.common.model.EmptyResponse;
import id.web.ilham.inventory.common.model.ResponseMessage;
import id.web.ilham.inventory.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAuthRegisterService implements CommonService<SignupRequest, EmptyResponse> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

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

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "error.role"));

        userRepository.save(user);
        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();

        userRoleRepository.save(userRole);

        return ResponseMessage.success(new EmptyResponse());
    }
}
