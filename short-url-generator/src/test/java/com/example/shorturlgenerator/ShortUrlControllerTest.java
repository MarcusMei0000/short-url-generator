package com.example.shorturlgenerator;

import com.example.shorturlgenerator.controller.ShortUrlController;
import com.example.shorturlgenerator.entity.Url;
import com.example.shorturlgenerator.repo.ShortUrlRepo;
import com.example.shorturlgenerator.service.ShortUrlService;
import com.example.shorturlgenerator.utils.Validator;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.message.BasicHttpResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShortUrlControllerTest {

    private static final Validator VALIDATOR = new Validator();

    public  static final long ID = 1L;
    public  static final String VALID_URL_STRING = "http://google.com";
    public  static final String INVALID_URL_STRING = "LAKVB";

    public static final Url  VALID_URL = new Url(ID, VALID_URL_STRING);

    public static final Url  INVALID_URL = new Url(ID, INVALID_URL_STRING);

    public static final String BASE_URL = "http://localhost:8080/";

    public  static final String ENCODED_ID = Long.toString(ID, 35);

    @Test
    public void generateShortUrlValid() {
        ShortUrlRepo shortUrlRepo = mock(ShortUrlRepo.class);
        when(shortUrlRepo.save(new Url(VALID_URL_STRING))).thenReturn(VALID_URL);

        ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepo);
        ShortUrlController urlController = new ShortUrlController(shortUrlService, VALIDATOR);

        ResponseEntity<String> actual = urlController.generateShortUrl(VALID_URL_STRING);
        ResponseEntity<String> expected = ResponseEntity.ok().body(BASE_URL + ENCODED_ID);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void generateShortUrlInvalid() {
        ShortUrlRepo shortUrlRepo = mock(ShortUrlRepo.class);
        when(shortUrlRepo.save(new Url(INVALID_URL_STRING))).thenReturn(INVALID_URL);

        ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepo);
        ShortUrlController urlController = new ShortUrlController(shortUrlService, VALIDATOR);

        ResponseEntity<String> actual = urlController.generateShortUrl(INVALID_URL_STRING);
        ResponseEntity<String> expected = ResponseEntity.badRequest().body("");

        Assertions.assertEquals(expected, actual);
    }
}
