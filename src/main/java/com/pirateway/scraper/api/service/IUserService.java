package com.pirateway.scraper.api.service;


import com.pirateway.scraper.exception.AuthenticationSecurityException;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public interface IUserService {

    @Transactional
    void create(
            @Nullable final User user
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final User user
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    User findByLogin(
            @Nullable final String login
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    User findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void clear(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<User> findAll(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    User authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException;
}