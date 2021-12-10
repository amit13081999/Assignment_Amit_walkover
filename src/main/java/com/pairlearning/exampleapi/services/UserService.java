package com.pairlearning.exampleapi.services;

import com.pairlearning.exampleapi.domain.User;
import com.pairlearning.exampleapi.exceptions.EtAuthException;

import java.util.List;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String email, String password) throws EtAuthException;

    void updatePassword(String email,String password,String newpassword ) throws EtAuthException;

    List<User> fetchAllUsers();

    void deleteUser(String email,String password) throws EtAuthException;
}
