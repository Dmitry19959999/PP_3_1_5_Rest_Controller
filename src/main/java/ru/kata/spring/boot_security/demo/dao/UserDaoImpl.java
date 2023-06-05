package ru.kata.spring.boot_security.demo.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext()
    private EntityManager entityManager;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserDaoImpl(RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        Set<Role> roleSet = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                Role foundRole = roleService.finedRoleById(role.getId());
                roleSet.add(foundRole);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleSet);
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = getUser(id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user, long id) {
        Set<Role> roleSet = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                Role foundRole = roleService.finedRoleById(role.getId());
                roleSet.add(foundRole);
            }
        }
        User userPreUpdate = getUser(id);
        if (!Objects.equals(userPreUpdate.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRoles(roleSet);
        entityManager.merge(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Query query = entityManager.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email);
        User user = (User) query.getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found", email));
        }
        user.getRoles().size();
        return user;
    }
}