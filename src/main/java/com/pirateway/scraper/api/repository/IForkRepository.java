package com.pirateway.scraper.api.repository;

import com.pirateway.scraper.model.entity.Fork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IForkRepository extends JpaRepository<Fork, String> {
}
