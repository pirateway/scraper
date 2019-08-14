package com.pirateway.scraper.exception;

import org.jetbrains.annotations.NotNull;

public class AuthenticationSecurityException extends Exception {

    public AuthenticationSecurityException(
            @NotNull final String message) {
        super("#" + message);
    }

}
