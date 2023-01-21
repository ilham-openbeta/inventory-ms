package id.web.ilham.inventory.authorization.repository.impl;

import id.web.ilham.inventory.authorization.entity.Role;
import id.web.ilham.inventory.authorization.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Role entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO role(name) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
                stmt.setString(1, entity.getName());
                return stmt;
            }, keyHolder);
            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            entity.setId(id);
        } else {
            String sql = "UPDATE role SET name=? WHERE id=?";
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, entity.getName());
                stmt.setInt(2, entity.getId());
                return stmt;
            });
        }
    }

    @Override
    public Boolean removeById(Integer id) {
        String sql = "DELETE FROM role WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Role findById(Integer id) {
        String sql = "SELECT id, name FROM role WHERE id = ?";

        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                Role.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build(), id));
    }

    @Override
    public List<Role> findAll() {
        String sql = "SELECT id, name FROM role";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Role.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
    }

    @Override
    public Optional<Role> findByName(String name) {
        String sql = "SELECT id, name FROM role WHERE name = ?";
        Role role = DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                Role.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build(), name));
        return Optional.ofNullable(role);
    }
}
