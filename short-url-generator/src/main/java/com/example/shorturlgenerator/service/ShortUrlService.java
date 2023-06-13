package com.example.shorturlgenerator.service;

import com.example.shorturlgenerator.entity.Url;
import com.example.shorturlgenerator.repo.ShortUrlRepo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ShortUrlService {
    public static final String BASE_URL = "http://localhost:8080/";

    public static final int RADIX = 35;

    private final ShortUrlRepo shortUrlRepo;

    private final Cache<String, String> cache = Caffeine.newBuilder()
                                                        .expireAfterWrite(15, TimeUnit.MINUTES)
                                                        .maximumSize(100)
                                                        .build();

    public ShortUrlService(@Autowired ShortUrlRepo shortUrlRepo) {
        this.shortUrlRepo = shortUrlRepo;
    }

    @Transactional
    public String generateShortUrl(final String fullUrl) {
        return cache.get(fullUrl, u -> {
            Url url = shortUrlRepo.findUrlByFullUrl(fullUrl);

            if (url != null) {
                return BASE_URL + Long.toString(url.getId(), RADIX);
            } else {
                Url savedUrl = shortUrlRepo.save(new Url(fullUrl));
                return BASE_URL + Long.toString(savedUrl.getId(), RADIX);
            }
        });
    }

    public Optional<String> findFullUrl(final String encoded) {
        long id = Long.parseLong(encoded, RADIX);
        return shortUrlRepo.findById(id).map(Url::getFullUrl);
    }
}
