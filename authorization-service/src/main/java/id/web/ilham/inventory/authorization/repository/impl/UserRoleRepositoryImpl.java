package id.web.ilham.inventory.authorization.repository.impl;

import id.web.ilham.inventory.authorization.entity.UserRole;
import id.web.ilham.inventory.authorization.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {


    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(UserRole entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
                stmt.setInt(1, entity.getUserId());
                stmt.setInt(2, entity.getRoleId());
                return stmt;
            }, keyHolder);
            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            entity.setId(id);
        } else {
            String sql = "UPDATE user_role SET user_id=?, role_id=? WHERE id=?";
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, entity.getUserId());
                stmt.setInt(2, entity.getRoleId());
                stmt.setInt(3, entity.getId());
                return stmt;
            });
        }
    }

    @Override
    public Boolean removeById(Integer id) {
        String sql = "DELETE FROM user_role WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public UserRole findById(Integer id) {
        String sql = "SELECT id, user_id, role_id FROM user_role WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                UserRole.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getInt("user_id"))
                        .roleId(rs.getInt("role_id"))
                        .build(), id));
    }

    @Override
    public List<UserRole> findAll() {
        String sql = "SELECT id, user_id, role_id FROM user_role";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                UserRole.builder()
                        .id(rs.getInt("id"))
                        .userId(rs.getInt("user_id"))
                        .roleId(rs.getInt("role_id"))
                        .build());
    }

    @Override
    public List<String> findRoles(int userId) {
        String sql = "select r.name as name from user_role ur " +
                "join role r on r.id = ur.role_id " +
                "where user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getString("name"), userId);
    }
}
