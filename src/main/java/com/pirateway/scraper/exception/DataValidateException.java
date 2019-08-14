package com.pirateway.scraper.exception;

import org.jetbrains.annotations.NotNull;

public class DataValidateException extends Exception {

    public DataValidateException(
            @NotNull final String message) {
        super("#" + message);
    }

}
