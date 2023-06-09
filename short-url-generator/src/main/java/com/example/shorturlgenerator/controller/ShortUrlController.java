package com.example.shorturlgenerator.controller;

import com.example.shorturlgenerator.service.ShortUrlService;
import com.example.shorturlgenerator.utils.Validator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

    //??? почему нельзя то, что в комментах
    @GetMapping("/l/{encodedId}")
    public void redirectToOriginal(@PathVariable String encodedId, HttpServletResponse httpServletResponse) {
        Optional<String> fullUrl = shortUrlService.findFullUrl(encodedId);
        if (fullUrl.isPresent()) {
            httpServletResponse.setHeader("Location",fullUrl.get());
            httpServletResponse.setStatus(302);
           //httpServletResponse.sendRedirect(fullUrl.get());
        } else {
            log.warn("No such link id " + encodedId);
            httpServletResponse.setStatus(400);
        }
    }

    @GetMapping("/f/{encodedId}")
    public ResponseEntity<Object> redirectToOriginal3(@PathVariable String encodedId) throws URISyntaxException {
        Optional<String> fullUrl = shortUrlService.findFullUrl(encodedId);
        if (fullUrl.isPresent()) {
            URI uri = new URI(fullUrl.get());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);

            return new ResponseEntity(httpHeaders, HttpStatus.SEE_OTHER);
        } else {
            log.warn("No such link id" + encodedId);
            return ResponseEntity.badRequest().body("");
        }
    }

    @GetMapping("/m/{encodedId}")
    public RedirectView redirectToOriginal2(@PathVariable String encodedId, RedirectAttributes attributes) {
        Optional<String> fullUrl = shortUrlService.findFullUrl(encodedId);
        if (fullUrl.isPresent()) {
            String ur = fullUrl.get();
            return new RedirectView(ur);
        } else {
            log.warn("No such link id" + encodedId);
            return new RedirectView();
        }
    }

    //49:00
    @PostMapping("/generate")
    public ResponseEntity<String> generateShortUrl(@RequestParam("link") String fullLink) {
        if (validator.isValidUrl(fullLink)) {
            String shortUrl = shortUrlService.generateShortUrl(fullLink);
            return ResponseEntity.ok().body(shortUrl);
        } else {
            log.warn("Link" + fullLink +"is not valid");
            return ResponseEntity.badRequest().body("");
        }
    }
}
