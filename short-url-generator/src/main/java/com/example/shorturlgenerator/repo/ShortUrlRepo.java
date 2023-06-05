package com.example.shorturlgenerator.repo;

import com.example.shorturlgenerator.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepo extends JpaRepository<Url, Long> {
    Url findUrlByFullUrl(final String fullUrl);

}
