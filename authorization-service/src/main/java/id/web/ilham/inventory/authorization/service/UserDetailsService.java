package id.web.ilham.inventory.authorization.service;

import id.web.ilham.inventory.authorization.entity.User;
import id.web.ilham.inventory.authorization.model.UserDetailsImpl;
import id.web.ilham.inventory.authorization.repository.UserRepository;
import id.web.ilham.inventory.authorization.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// this class is needed to validate login using Authentication Manager
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<String> roles = userRoleRepository.findRoles(user.getId());

        return UserDetailsImpl.build(user, roles);
    }

}