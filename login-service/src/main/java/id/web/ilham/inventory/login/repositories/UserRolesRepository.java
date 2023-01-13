package id.web.ilham.inventory.login.repositories;

import id.web.ilham.inventory.common.repository.CommonRepository;
import id.web.ilham.inventory.login.entities.UserRoles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends CommonRepository<UserRoles, Integer> {
    List<String> findRoles(int userId);
}
