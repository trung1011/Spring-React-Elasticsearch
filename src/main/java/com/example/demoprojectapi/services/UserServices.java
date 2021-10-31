package com.example.demoprojectapi.services;

import com.example.demoprojectapi.exceptions.EtAuthException;
import com.example.demoprojectapi.models.User;

public interface UserServices {
    User validateUser (String email, String password)throws EtAuthException;

    User registerUser(String firstName, String lastName, String email , String password) throws EtAuthException;
}
