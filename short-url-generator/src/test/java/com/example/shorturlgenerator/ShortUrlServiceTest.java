package com.example.shorturlgenerator;

import com.example.shorturlgenerator.entity.Url;
import com.example.shorturlgenerator.repo.ShortUrlRepo;
import com.example.shorturlgenerator.service.ShortUrlService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShortUrlServiceTest {
    public  static final long ID = 1L;
    public  static final String ENCODED_ID = Long.toString(ID, 35);
    public  static final String FULL_URL = "http://google.com";

    public static final String BASE_URL = "http://localhost:8080/";

    public static final Url  URL = new Url(ID, FULL_URL);
    @Test
    public void generateShortUrl() {
        ShortUrlRepo shortUrlRepo = mock(ShortUrlRepo.class);
        when(shortUrlRepo.save(new Url(FULL_URL))).thenReturn(URL);
        ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepo);
        String actual = shortUrlService.generateShortUrl(FULL_URL);
        String expected = BASE_URL + ID;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateExistedShortUrl() {
        ShortUrlRepo shortUrlRepo = mock(ShortUrlRepo.class);
        when(shortUrlRepo.save(new Url(FULL_URL))).thenReturn(URL);
        ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepo);
        String expected = shortUrlService.generateShortUrl(FULL_URL);
        String actual = shortUrlService.generateShortUrl(FULL_URL);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findFullUrl() {
        ShortUrlRepo shortUrlRepo = mock(ShortUrlRepo.class);
        when(shortUrlRepo.findById(ID))
                .thenReturn(Optional.of(new Url(ID,FULL_URL)));
        ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepo);
        Optional<String> fullUrl = shortUrlService.findFullUrl(ENCODED_ID);

        Assertions.assertTrue(fullUrl.isPresent());
        Assertions.assertEquals(FULL_URL, fullUrl.get());
    }
}
