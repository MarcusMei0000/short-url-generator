package com.example.shorturlgenerator.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {
    private static final String REG_EXP = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
    private static final Pattern pattern = Pattern.compile(REG_EXP);
    public boolean isValidUrl(final String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

}

