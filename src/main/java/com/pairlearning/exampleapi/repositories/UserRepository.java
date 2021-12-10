package com.pairlearning.exampleapi.repositories;

import com.pairlearning.exampleapi.domain.User;
import com.pairlearning.exampleapi.exceptions.EtAuthException;

import java.util.List;

public interface UserRepository {
    Integer create(String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);

    void update(String email,String password,String newpassword ) throws EtAuthException;

    List<User> findAll() throws EtAuthException;

    void removeUser(String email,String password ) throws EtAuthException;

}
