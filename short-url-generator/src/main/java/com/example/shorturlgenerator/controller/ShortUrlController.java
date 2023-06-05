package com.example.shorturlgenerator.controller;


import com.example.shorturlgenerator.service.ShortUrlService;
import com.example.shorturlgenerator.utils.Validator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.http.HttpRequest;
import java.util.Optional;

@Controller
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    private final Validator validator;
    private static final Logger log =  LoggerFactory.getLogger(ShortUrlController.class);


    public ShortUrlController(@Autowired ShortUrlService shortUrlService, Validator validator) {
        this.shortUrlService = shortUrlService;
        this.validator = validator;
    }
    @GetMapping("/l/{encodedId}")
    public void redirectToOriginal(@PathVariable String encodedId, HttpServletResponse httpServletResponse) {
        Optional<String> fullUrl = shortUrlService.findFullUrl(encodedId);
        if (fullUrl.isPresent()) {
            httpServletResponse.setHeader("Location",fullUrl.get());
            httpServletResponse.setStatus(302);
        } else {
            log.warn("No such link id" + encodedId);
            httpServletResponse.setStatus(400);
        }
    }

    //49:00
    @PostMapping("/generate")
    public ResponseEntity<String> generateShortUrl(@RequestBody String fullLink) {
        if (validator.isValidUrl(fullLink)) {
            String shortUrl = shortUrlService.generateShortUrl(fullLink);
            return ResponseEntity.ok().body(shortUrl);
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }
}
