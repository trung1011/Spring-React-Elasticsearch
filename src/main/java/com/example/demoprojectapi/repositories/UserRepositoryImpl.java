package com.example.demoprojectapi.repositories;

import com.example.demoprojectapi.exceptions.EtAuthException;
import com.example.demoprojectapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements  UserRespository{

    private  static  String SQL_CREATE ="Insert into users(id,first_name , last_name, email, password) values (nextval('users_seq') , ? ,?,? , ?)";
    private  static  String SQL_COUNT_BY_EMAIL="Select Count(*) from users where email=?";
    private  static  String SQL_FIND_BY_ID="select id, first_name,last_name,email, password From users where id = ?";
    private  static  String SQL_FIND_USER_BY_EMAIL = "select id,first_name, last_name, email , password  from users where email= ? ";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer createUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        try {
            System.out.println(firstName);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(SQL_CREATE , Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, password);
                System.out.println(statement);
                return statement;

            },keyHolder);
            return (Integer)  keyHolder.getKeys().get("id");
        }catch (Exception e){
            throw  new EtAuthException("Failed to create ");
        }
    }

    @Override
    public User getUser(String email, String password) throws EtAuthException {
        return null;
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email} , Integer.class);
    }

    @Override
    public User findUser(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId} , userRowMapper);

    }

    @Override
    public  User findUserByEmailAndPassword (String email , String password){
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_EMAIL, new Object[]{email} , userRowMapper);
            System.out.println(user.getEmail());
            System.out.println(user.getPassword());
            System.out.println(password);
            if(!password.equals(user.getPassword())){
                throw  new EtAuthException("invalid email/password");
            }
            return user;
        }catch (EmptyResultDataAccessException e){
            throw  new EtAuthException("invalid email/password");

        }
    }

    private RowMapper<User> userRowMapper =((rs, rowNum) -> {

        return new User(rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password")
                );
    });
}
