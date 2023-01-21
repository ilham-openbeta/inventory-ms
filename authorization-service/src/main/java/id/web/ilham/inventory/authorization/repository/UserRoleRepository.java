package id.web.ilham.inventory.authorization.repository;

import id.web.ilham.inventory.authorization.entity.UserRole;
import id.web.ilham.inventory.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CommonRepository<UserRole, Integer> {
    List<String> findRoles(int userId);
}
