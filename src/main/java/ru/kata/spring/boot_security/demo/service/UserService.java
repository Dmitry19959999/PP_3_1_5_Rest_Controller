package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void createUser(User user);

    void deleteUser(long id);

    void updateUser(User user, long id);

    List<User> getAllUsers();

    User getUser(long id);

    UserDetails loadUserByUsername(String email);

}