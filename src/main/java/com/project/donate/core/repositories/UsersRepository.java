package com.project.donate.core.repositories;

import com.project.donate.core.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);
}
