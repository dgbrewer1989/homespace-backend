package com.space.backend.space.apis.service;

import com.space.backend.space.apis.models.profile.Profile;
import com.space.backend.space.apis.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Profile getProfile(String username) throws Exception {
        Profile profile = jdbcTemplate.queryForObject(
                "SELECT " +
                        "profile.id as profile_id," +
                        "users.id as user_id," +
                        "users.first_name," +
                        "users.last_name," +
                        "users.birthday," +
                        "users.username " +
                        "FROM profile " +
                        "INNER JOIN users on profile.user_id = users.id " +
                        " WHERE users.username = ?;", new Object[]{ username },
                new ProfileMapper());

        if (profile == null) {
            throw new Exception("No results found for username " + username);
        }

        return profile;
    }

    public List<Profile> getAllProfiles() {
        return jdbcTemplate.query(
                "SELECT " +
                        "profile.id as profile_id, " +
                        "users.id as user_id, " +
                        "users.first_name," +
                        "users.last_name," +
                        "users.birthday," +
                        "users.username " +
                        "FROM profile " +
                        "INNER JOIN users on profile.user_id = users.id;",
                new ProfileMapper());
    }

    private class ProfileMapper implements RowMapper<Profile> {

        @Override
        public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
            Profile profile = new Profile();
            profile.setProfileId(UUID.fromString(rs.getString("profile_id")));
            User user = User.mapping(rs);

            profile.setUser(user);

            return profile;
        }
    }
}
