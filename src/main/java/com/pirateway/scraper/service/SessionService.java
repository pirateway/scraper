package com.pirateway.scraper.service;

import com.pirateway.scraper.api.service.ISessionService;
import com.pirateway.scraper.api.service.IUserService;
import com.pirateway.scraper.enumerate.Role;
import com.pirateway.scraper.exception.AuthenticationSecurityException;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.Session;
import com.pirateway.scraper.model.entity.User;
import com.pirateway.scraper.util.FieldConst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService implements ISessionService {

    @Autowired
    IUserService userService;

    @Override
    public void
    validate(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        if (session.getAttribute(FieldConst.USER) == null)
            throw new AuthenticationSecurityException("Session is invalid: \nDoes not found logged user! \nPlease re-userLogin!");
    }

    @Override
    public void validateAdmin(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException {
        validate(session);
        @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
        if (!loggedUser.getRole().equals(Role.ADMIN))
            throw new AuthenticationSecurityException("Forbidden action for your role!");

    }

    @Override
    public boolean isUser(@Nullable final HttpSession session) {
        @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
        if (loggedUser == null) return false;
        return true;
    }

    @Override
    public boolean isAdmin(@Nullable HttpSession session) {
        @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
        if (loggedUser == null || loggedUser.getRole() != Role.ADMIN) return false;
        return true;
    }

    @Override
    public User getLoggedUser(@Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        @Nullable final User loggedUser = (User) session.getAttribute(FieldConst.USER);
        return loggedUser;
    }

    @Override
    public void validateEndpointSession(
            @NotNull final Session session
    ) throws DataValidateException {
        userService.findOne(session.getUserId());
    }

    @Override
    public void validateEndpointAdminSession(
            @NotNull Session session
    ) throws AuthenticationSecurityException, DataValidateException {
        @NotNull final User loggedUser = userService.findOne(session.getUserId());
        if (loggedUser.getRole() != Role.ADMIN)
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }
}
