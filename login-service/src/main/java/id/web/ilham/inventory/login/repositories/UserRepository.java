package id.web.ilham.inventory.login.repositories;


import id.web.ilham.inventory.common.repository.CommonRepository;
import id.web.ilham.inventory.login.entities.User;

import java.util.Optional;

public interface UserRepository extends CommonRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
