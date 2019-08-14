package com.pirateway.scraper.service;


import com.pirateway.scraper.api.repository.IUserRepository;
import com.pirateway.scraper.api.service.IUserService;
import com.pirateway.scraper.exception.AuthenticationSecurityException;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.User;
import com.pirateway.scraper.util.DataValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    @NotNull
    private final IUserRepository userRepository;

    @Autowired
    public UserService(
            @NotNull final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(
            @Nullable final User user
    ) throws DataValidateException {
        DataValidator.validateUser(user, true);
        @NotNull final User findUser = userRepository
                .findByLogin(user.getLogin());
        if (findUser != null)
            throw new DataValidateException("User with login: '" + user.getLogin() + "' already exist!");
        userRepository
                .save(user);
    }

    @Override
    @Transactional
    public void edit(
            @Nullable final User editUser
    ) throws DataValidateException {
        DataValidator.validateUser(editUser, false);
        @Nullable final User user = userRepository
                .findById(editUser.getId()).get();
        if (user == null) throw new DataValidateException("User not found");
        @NotNull final User findUser = userRepository
                .findByLogin(editUser.getLogin());
        if (findUser != null && !user.getId().equals(findUser.getId()))
            throw new DataValidateException("User with userLogin: '" + editUser.getLogin() + "' already exist!");
        if (DataValidator.stringIsNull(editUser.getPassword()))
            editUser.setPassword(user.getPassword());
        user.setName(editUser.getName());
        user.setDescription(editUser.getDescription());
        user.setLogin(editUser.getLogin());
        user.setPassword(editUser.getPassword());
        user.setRole(editUser.getRole());
        userRepository
                .save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(
            @Nullable final String login
    ) throws DataValidateException {
        DataValidator.validateString(login);
        @Nullable final User user = userRepository
                .findByLogin(login);
        if (user == null) throw new DataValidateException("User not found");
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final User user = userRepository
                .findById(id).get();
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable User user = userRepository
                .findById(id).get();
        if (user == null) throw new DataValidateException("User not found!");
        userRepository
                .delete(user);
    }

    @Transactional
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<User> users = userRepository
                .findAll();
        if (users == null) throw new DataValidateException("Users not found!");
        users.forEach(userRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<User> users = userRepository
                .findAll();
        if (users == null) throw new DataValidateException("Users not found!");
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateString(login, password);
        @Nullable final User user = userRepository
                .findByLogin(login);
        if (user == null) throw new AuthenticationSecurityException("Wrong user name!");
        if (!user.getPassword().equals(password)) throw new AuthenticationSecurityException("Wrong password!");
        return user;
    }
}