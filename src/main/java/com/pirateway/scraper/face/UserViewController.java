package com.pirateway.scraper.face;

import com.pirateway.scraper.api.service.ISessionService;
import com.pirateway.scraper.api.service.IUserService;
import com.pirateway.scraper.enumerate.Role;
import com.pirateway.scraper.exception.AuthenticationSecurityException;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.User;
import com.pirateway.scraper.util.DataValidator;
import com.pirateway.scraper.util.FieldConst;
import com.pirateway.scraper.util.HashUtil;
import com.pirateway.scraper.util.OptionsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@SessionScope
public class UserViewController {

    @NotNull
    @Autowired
    private IUserService userService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @Nullable
    private Collection<User> users;

    @Nullable
    private User selectedUser;

    @NotNull
    private User editUser;

    @Nullable
    private String login;

    @Nullable
    private String password;

    public void userLogin(
    ) {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        try {
            @NotNull final User loggedUser = userService.authenticationUser(login, HashUtil.md5(password));
            @NotNull final HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful:", "Welcome " + login));
            session.setAttribute(FieldConst.USER, loggedUser);
        } catch (AuthenticationSecurityException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication Error:", e.getMessage()));
        } catch (DataValidateException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }

    public String userLogout() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "mainOutcome";
    }

    public Collection<User> getUsers() throws AuthenticationSecurityException, DataValidateException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateAdmin(session);
        users = userService.findAll();
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public void userCreate() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        editUser = new User(
                "New User:",
                "password",
                "New user name",
                "Description for new user",
                Role.USER);
        PrimeFaces.current().dialog().openDynamic("userEditOutcome", OptionsUtil.getWindowOptions(), null);
    }

    @Nullable
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(
            @Nullable final User selectedUser
    ) {
        this.selectedUser = selectedUser;
    }

    @NotNull
    public User getEditUser() throws DataValidateException, AuthenticationSecurityException {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        sessionService.validateAdmin(session);
        return editUser;
    }

    public void setEditUser(
            @NotNull final User editUser
    ) {
        this.editUser = editUser;
    }

    public void onRowSelect(SelectEvent event) {
        selectedUser = ((User) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedUser = null;
    }

    public void userDelete() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = sessionService.getLoggedUser(session);
            if (selectedUser == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No user selected!"));
                return;
            }
            userService.remove(selectedUser.getId());
            PrimeFaces.current().dialog().closeDynamic("userEditOutcome");
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        } catch (AuthenticationSecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication Error:", e.getMessage()));
        }

    }

    public void userEdit() {
        if (selectedUser == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", "No user selected!"));
            return;
        }
        editUser = selectedUser;
        PrimeFaces.current().dialog().openDynamic("userEditOutcome", OptionsUtil.getWindowOptions(), null);
    }

    public void editProfile() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        @NotNull final HttpSession session = (HttpSession) context
                .getExternalContext()
                .getSession(false);
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = sessionService.getLoggedUser(session);
            selectedUser = userService.findOne(loggedUser.getId());
            userEdit();
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        } catch (AuthenticationSecurityException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Authentication Error:", e.getMessage()));
        }
    }

    public Role[] getRoles() {
        return Role.values();
    }

    public void userSave() {
        @Nullable final String password = editUser.getPassword();
        if (!DataValidator.stringIsNull(password))
            editUser.setPassword(HashUtil.md5(password));
        try {
            if (editUser == selectedUser) {//equality to reference
                userService.edit(editUser);
            } else {
                userService.create(editUser);
            }
            PrimeFaces.current().dialog().closeDynamic("userEditOutcome");
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }

    @Nullable
    public String getLogin() {
        return login;
    }

    public void setLogin(@Nullable String login) {
        this.login = login;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }
}
