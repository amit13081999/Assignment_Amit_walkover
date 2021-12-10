package com.pairlearning.exampleapi.repositories;

import com.pairlearning.exampleapi.domain.User;
import com.pairlearning.exampleapi.exceptions.EtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


@Repository
public class UserRepositoryimpl implements UserRepository{

    private  static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID,EMAIL,PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'),?,?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, EMAIL, PASSWORD FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID,EMAIL,PASSWORD FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_UPDATE = "UPDATE ET_USERS SET PASSWORD = ? WHERE EMAIL = ? AND PASSWORD = ?";
    private static final String SQL_FIND_ALL = "SELECT USER_ID, EMAIL, PASSWORD FROM ET_USERS";
    private static final String SQL_DELETE = "DELETE FROM ET_USERS WHERE EMAIL = ? AND PASSWORD = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(String email, String password) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, password);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");

        }catch (Exception e){
            throw new EtAuthException("invalid details.failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try {
           User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
           if (!password.equals(user.getPassword()))
               throw new EtAuthException("Invalid email/password");
           return user;
        }catch (EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public void update(String email, String password, String newpassword) throws EtAuthException {

        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{newpassword, email, password});
        }catch (Exception e){
            throw new EtAuthException("invalid request");

        }
    }

    @Override
    public List<User> findAll() throws EtAuthException {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public void removeUser(String email, String password) throws EtAuthException {
        jdbcTemplate.update(SQL_DELETE, new Object[]{email, password});

    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt( "USER_ID"),
                 rs.getString( "EMAIL"),
                 rs.getString( "PASSWORD"));

});
}

