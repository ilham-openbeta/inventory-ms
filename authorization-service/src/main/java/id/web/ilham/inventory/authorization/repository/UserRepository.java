package id.web.ilham.inventory.authorization.repository;


import id.web.ilham.inventory.authorization.entity.User;
import id.web.ilham.inventory.common.repository.CommonRepository;

import java.util.Optional;

public interface UserRepository extends CommonRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
