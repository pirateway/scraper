package com.pirateway.scraper.api.service;

import com.pirateway.scraper.exception.AuthenticationSecurityException;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.Session;
import com.pirateway.scraper.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface ISessionService {

    void validate(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException;

    void validateAdmin(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException;

    boolean isUser(
            @Nullable final HttpSession session);

    boolean isAdmin(
            @Nullable final HttpSession session);

    User getLoggedUser(
            @Nullable final HttpSession session) throws AuthenticationSecurityException;

    void validateEndpointSession(
            @NotNull final Session session
    ) throws DataValidateException;

    void validateEndpointAdminSession(
            @NotNull final Session session
    ) throws AuthenticationSecurityException, DataValidateException;
}
