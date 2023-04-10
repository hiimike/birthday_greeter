package com.example.birthdaygreeter.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValidatorTest {

    @Test
    public void isValidDate_ShouldReturnTrueForValidDates() {
        assertTrue(DateValidator.isValidDate("2021/12/31"));
        assertTrue(DateValidator.isValidDate("2000/02/29"));
    }

    @Test
    public void isValidDate_ShouldReturnFalseForInvalidDates() {
        //assertFalse(DateValidator.isValidDate("2001/02/29"));
        assertFalse(DateValidator.isValidDate("2021/13/01"));
        assertFalse(DateValidator.isValidDate("2021-12-31"));
        assertFalse(DateValidator.isValidDate("2021/12/32"));
        assertFalse(DateValidator.isValidDate("2021/12/3"));
        assertFalse(DateValidator.isValidDate("2021/2/03"));
        assertFalse(DateValidator.isValidDate("21/12/03"));
    }
}
