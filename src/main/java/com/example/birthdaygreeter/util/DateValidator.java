package com.example.birthdaygreeter.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class DateValidator {
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");

    public static boolean isValidDate(String dateStr) {
        if (!DATE_PATTERN.matcher(dateStr).matches()) {
            return false;
        }

        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}

