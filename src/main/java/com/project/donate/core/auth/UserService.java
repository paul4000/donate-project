package com.project.donate.core.auth;

import com.project.donate.core.models.User;
import com.project.donate.core.models.dtos.UserTO;
import com.project.donate.core.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        Assert.notNull(usersRepository, "UsersRepository should not be null");
        Assert.notNull(bCryptPasswordEncoder, "BCryptPasswordEncoder should not be null");

        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser(User user){

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);

    }
}
