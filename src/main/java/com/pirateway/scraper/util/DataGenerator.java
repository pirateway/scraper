package com.pirateway.scraper.util;

import com.pirateway.scraper.api.service.ISessionService;
import com.pirateway.scraper.api.service.IUserService;
import com.pirateway.scraper.enumerate.Role;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

    @NotNull
    private final IUserService userService;

    @NotNull
    private final ISessionService sessionService;

    @Autowired
    public DataGenerator(
            @NotNull final IUserService userService,
            @NotNull final ISessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
        generate();
    }

    public void generate() {
        cleanUp();
        generateUsers();
    }

    public void generateUsers() {
        try {
            User admin = new User(
                    "admin",
                    HashUtil.md5("admin"),
                    "Administrator",
                    "Application's administrator",
                    Role.ADMIN);

            User user = new User("user",
                    HashUtil.md5("user"),
                    "User",
                    "Application's user",
                    Role.USER);

            User user2 = new User("Иван",
                    HashUtil.md5("Иван"),
                    "Иван",
                    "ИванИванИванИванИванИванИванИванИванИванИван",
                    Role.USER);
            userService.create(admin);
            userService.create(user);
            userService.create(user2);

        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }

    public void cleanUp(
    ) {
        try {
            userService.clear();
        } catch (DataValidateException e) {
            e.printStackTrace();
        }
    }
}
