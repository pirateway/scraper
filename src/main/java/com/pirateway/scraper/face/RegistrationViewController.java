package com.pirateway.scraper.face;

import com.pirateway.scraper.api.service.IUserService;
import com.pirateway.scraper.enumerate.Role;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.User;
import com.pirateway.scraper.util.DataValidator;
import com.pirateway.scraper.util.HashUtil;
import com.pirateway.scraper.util.OptionsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Controller
public class RegistrationViewController {

    @NotNull
    private User registeredUser;

    @Autowired
    IUserService userService;

    @NotNull
    public User getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(@NotNull User registeredUser) {
        this.registeredUser = registeredUser;
    }

    public void registrationUser() {
        @NotNull final FacesContext context = FacesContext.getCurrentInstance();
        registeredUser = new User(
                "New User",
                "password",
                "New user name",
                "Description for new user",
                Role.USER);
        PrimeFaces.current().dialog().openDynamic("registrationOutcome", OptionsUtil.getWindowOptions(), null);
    }

    public void saveUser() {
        @Nullable final String password = registeredUser.getPassword();
        if (!DataValidator.stringIsNull(password))
            registeredUser.setPassword(HashUtil.md5(password));
        try {
            userService.create(registeredUser);
            PrimeFaces.current().dialog().closeDynamic("registrationOutcome");
        } catch (DataValidateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Input Error:", e.getMessage()));
        }
    }
}
