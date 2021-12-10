package com.pairlearning.exampleapi.services;

import com.pairlearning.exampleapi.domain.User;
import com.pairlearning.exampleapi.exceptions.EtAuthException;
import com.pairlearning.exampleapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceimpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
      if(email != null) email = email.toLowerCase();
      return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if (!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid Email");
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new EtAuthException("Email already Registered");
        Integer userId = userRepository.create(email,password);
        return  userRepository.findById(userId);


    }

    @Override
    public void updatePassword(String email, String password, String newpassword) throws EtAuthException {
        User user = userRepository.findByEmailAndPassword(email, password);
        userRepository.update(email, password, newpassword);
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String email, String password) throws EtAuthException {
        userRepository.removeUser(email, password);

    }
}
