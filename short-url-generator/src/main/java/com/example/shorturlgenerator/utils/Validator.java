package com.example.shorturlgenerator.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {
    private static final Pattern pattern = Pattern.compile("https?://[a-zA-Z0-9.-_/]*");
    public boolean isValidUrl(final String url) {
        Matcher matchar = pattern.matcher(url);
        //return matchar.matches();
        return true;
    }

}

