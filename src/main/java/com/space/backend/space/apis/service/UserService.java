package com.space.backend.space.apis.service;

import com.space.backend.space.apis.models.authenticate.UserAuthenticate;
import com.space.backend.space.apis.models.users.CreateUserModel;
import com.space.backend.space.apis.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createUser(CreateUserModel user) throws Exception {
        insertUser(user);
    }

    public void insertUser(CreateUserModel user) throws Exception {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("first_name", user.getFirstName());
        parameters.put("last_name", user.getLastName());
        parameters.put("birthday", user.getBirthday());
        parameters.put("enabled", true);

        KeyHolder key = jdbcInsert.executeAndReturnKeyHolder(new MapSqlParameterSource(parameters));
        UUID userId = (UUID) key.getKeyList().get(0).get("id");

        if (userId != null) {
            jdbcTemplate.update(
                    "INSERT INTO profile " +
                            "(user_id) " +
                            "VALUES (?);",
                    userId
            );
        } else {
            throw new Exception("User could not be created. User id null for user: " + user.toString());
        }
    }

    public User getUser(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT " +
                        "id, " +
                        "first_name, " +
                        "last_name, " +
                        "username, " +
                        "birthday " +
                        "from users WHERE id = ?", new Object[]{id},
                new UserMapper()
        );
    }

    public Boolean getUserAuthenticate(UserAuthenticate user) {
        return jdbcTemplate.queryForObject(
                "SELECT " +
                        " 1 " +
                        "from users " +
                        "WHERE " +
                        "username = ? " +
                        "AND password = ? ", new Object[]{user.getUsername(), user.getPassword()},
                Boolean.class
        );
    }

    public class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(rs.getString("id")));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setBirthday(rs.getDate("birthday"));

            return user;
        }
    }
}
