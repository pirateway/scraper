package com.pirateway.scraper.face;

import com.pirateway.scraper.api.service.ISessionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Controller
public class ElementViewController {

    @NotNull
    private boolean visibleForUser = false;

    @NotNull
    private boolean visibleForAdmin = false;

    @NotNull
    @Autowired
    ISessionService sessionService;

    public boolean getVisibleForUser() {
        @NotNull final HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSession(false);
        visibleForUser = sessionService.isUser(session);
        return visibleForUser;
    }

    public void setVisibleForUser(boolean visibleForUser) {
        this.visibleForUser = visibleForUser;
    }

    public boolean getVisibleForAdmin() {
        @NotNull final HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSession(false);
        visibleForAdmin = sessionService.isAdmin(session);
        return visibleForAdmin;
    }

    public void setVisibleForAdmin(boolean visibleForAdmin) {
        this.visibleForAdmin = visibleForAdmin;
    }

}
