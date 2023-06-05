package com.example.shorturlgenerator;

import com.example.shorturlgenerator.entity.Url;
import com.example.shorturlgenerator.repo.ShortUrlRepo;
import com.example.shorturlgenerator.service.ShortUrlService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class ShortUrlServiceTest {
    public  static final long ID = 1L;
    public  static final String ENCODED_ID = Long.toString(ID, 35);
    public  static final String FULL_URL = "https://google.com";
    @Test
    public void generateShortUrl() {
        //существует, не существует
    }

    @Test
    public void findFullUrl() {
        //шоб не лезть в базу
        ShortUrlRepo shortUrlRepo = Mockito.mock(ShortUrlRepo.class);
        when(shortUrlRepo.findById(ID))
                .thenReturn(Optional.of(new Url(ID,FULL_URL)));
        ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepo);
        Optional<String> fullUrl = shortUrlService.findFullUrl(ENCODED_ID);
        Assertions.assertTrue(fullUrl.isPresent());
        Assertions.assertEquals(FULL_URL, fullUrl.get());
    }
}
