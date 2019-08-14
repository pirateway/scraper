package com.pirateway.scraper.util;

import com.pirateway.scraper.enumerate.Role;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.Fork;
import com.pirateway.scraper.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DataValidator {
    public static boolean stringIsNull(
            @Nullable final String string) {
        if (string == null || string.isEmpty() || "null".equals(string))
            return true;
        return false;
    }

    public static void validateString(
            @Nullable final String... strings)
            throws DataValidateException {
        for (String string : strings) {
            if (string == null || string.isEmpty())
                throw new DataValidateException("Input parameter not must be null or empty!");
        }
    }

    public static void validateRole(
            @Nullable final Role role)
            throws DataValidateException {
        if (role == null) throw new DataValidateException("Incorrect input role!");
    }

    public static void validateParameter(
            @Nullable final String parameter)
            throws DataValidateException {
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("order", "order");
        parameters.put("dateBegin", "dateBegin");
        parameters.put("dateEnd", "dateEnd");
        parameters.put("status", "status");
        if (parameters.get(parameter) == null) throw new DataValidateException("Incorrect input parameter!");
    }

    public static void validateUser(
            @Nullable final User user,
            @NotNull final boolean validatePassword)
            throws DataValidateException {
        if (user == null)
            throw new DataValidateException("Incorrect user dto!");
        if (user.getId() == null || user.getId().isEmpty())
            throw new DataValidateException("Id user not must be empty!");
        if (user.getLogin() == null || user.getLogin().isEmpty())
            throw new DataValidateException("User userLogin not must be empty!");
        if (validatePassword)
            if (user.getPassword() == null || user.getPassword().isEmpty())
                throw new DataValidateException("User password not must be empty!");
        validateRole(user.getRole());
    }

    public static void validateFork(
            @Nullable final Fork fork
    ) throws DataValidateException {
        if (fork == null)
            throw new DataValidateException("Incorrect fork!");
        if (fork.getId() == null || fork.getId().isEmpty())
            throw new DataValidateException("Ford id not must be empty!");
    }
}
