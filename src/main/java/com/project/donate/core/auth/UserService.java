package com.project.donate.core.auth;

import com.project.donate.core.model.Project;
import com.project.donate.core.model.Role;
import com.project.donate.core.model.User;
import com.project.donate.core.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public User saveNewUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);

    }

    public User addProjectToUser(String username, Project project) {

        User byUsername = usersRepository.findByUsername(username);
        byUsername.addProject(project);

        return usersRepository.save(byUsername);

    }

    public String getWalletName(String username) {

        return usersRepository.findByUsername(username)
                .getWalletFile();
    }

    public boolean checkIfExist(String email, String username) {

        User byUsername = usersRepository.findByUsernameOrEmail(username, email);

        return Optional.ofNullable(byUsername).isPresent();
    }

    public User getUserFromDatabase(String username) {
        User byUsername = usersRepository.findByUsername(username);

        return byUsername;
    }

    public User getByAddress(String address) {

        return usersRepository.findByAccount(address);
    }

    public List<User> getAllExecutors() {
        Iterable<User> all = usersRepository.findAll();

        return StreamSupport.stream(all.spliterator(), false)
                .filter(user -> user.getRole().equals(Role.EXECUTOR))
                .collect(Collectors.toList());
    }
}
