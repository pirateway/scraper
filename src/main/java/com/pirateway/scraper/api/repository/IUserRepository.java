package com.pirateway.scraper.api.repository;

import com.pirateway.scraper.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    User findByLogin(
            @NotNull final String login);
}