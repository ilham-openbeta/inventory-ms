package id.web.ilham.inventory.login.repositories;

import id.web.ilham.inventory.common.repository.CommonRepository;
import id.web.ilham.inventory.login.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CommonRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
