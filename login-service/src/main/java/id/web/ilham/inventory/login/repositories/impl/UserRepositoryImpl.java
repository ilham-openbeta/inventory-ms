package id.web.ilham.inventory.login.repositories.impl;

import id.web.ilham.inventory.login.entities.User;
import id.web.ilham.inventory.login.repositories.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(User entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO user(username, email, password) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"id"});
                stmt.setString(1, entity.getUsername());
                stmt.setString(2, entity.getEmail());
                stmt.setString(3, entity.getPassword());
                return stmt;
            }, keyHolder);
            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            entity.setId(id);
        } else {
            String sql = "UPDATE user SET username=?, email=?, password=? WHERE id=?";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, entity.getUsername());
                stmt.setString(2, entity.getEmail());
                stmt.setString(3, entity.getPassword());
                stmt.setInt(4, entity.getId());
                return stmt;
            });
        }
    }

    @Override
    public Boolean removeById(Integer id) {
        String sql = "DELETE FROM user WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public User findById(Integer id) {
        String sql = "SELECT id, username, email, password FROM user WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                User.builder()
                        .id(rs.getInt("id"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build(), id));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, username, email, password FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                User.builder()
                        .id(rs.getInt("id"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, email, password FROM user WHERE username = ?";
        User user = DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) ->
                User.builder()
                        .id(rs.getInt("id"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build(), username));
        return Optional.ofNullable(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        String sql = "SELECT 1 FROM user WHERE username = ? LIMIT 1";
        Integer count = DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("1"), username));
        if (count != null) {
            return count > 0;
        } else {
            return false;
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM user WHERE email = ? LIMIT 1";
        Integer count = DataAccessUtils.singleResult(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("1"), email));
        if (count != null) {
            return count > 0;
        } else {
            return false;
        }
    }
}
