package id.web.ilham.inventory.authorization.repository;

import id.web.ilham.inventory.authorization.entity.Role;
import id.web.ilham.inventory.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CommonRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
