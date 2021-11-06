package com.example.demoprojectapi.repositories;

import com.example.demoprojectapi.exceptions.EtAuthException;
import com.example.demoprojectapi.models.User;

public interface UserRespository {
    Integer createUser(String firsName, String lastName, String email, String password) throws EtAuthException;

    User getUser(String email , String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findUser(Integer userId);
    User findUserByEmailAndPassword (String email , String password);

}
