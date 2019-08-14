package com.pirateway.scraper.face;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;

@Controller
public class ExceptionHandlerController {

    public String getStatusCode() {
        @Nullable final Object code = FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.status_code");
        if (code == null) return null;
        return String.valueOf((Integer) code);
    }

    public String getMessage() {
        @Nullable final Object message = FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.message");

        if (message == null) return null;
        @NotNull final int start = message.toString().lastIndexOf('#') + 1;
        return message.toString().substring(start);
    }

    public String getExceptionType() {
        @Nullable final Object type = FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.exception_type");
        if (type == null) return null;
        return type.toString();
    }

    public String getException() {
        @Nullable final Object exception = FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.exception");
        if (exception == null) return null;
        return ((Exception) exception).toString();
    }

    public String getRequestURI() {
        @Nullable final Object uri = FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.request_uri");
        if (uri == null) return null;
        return uri.toString();
    }

    public String getServletName() {
        @Nullable final Object servlet = FacesContext.getCurrentInstance().getExternalContext().
                getRequestMap().get("javax.servlet.error.servlet_name");
        if (servlet == null) return null;
        return servlet.toString();
    }

}
