package com.example.birthdaygreeter.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DateUtils {

    public static LocalDate adjustBirthdate(LocalDate birthDate) {
        if (birthDate.getMonth() == Month.FEBRUARY && birthDate.getDayOfMonth() == 29) {
            return birthDate.withDayOfMonth(28);
        }
        return birthDate;
    }

    public static List<Integer> getAdjustDays(LocalDate today) {
        if (today.getMonth() == Month.FEBRUARY && today.getDayOfMonth() == 28) {
            return List.of(28, 29);
        }
        return List.of(today.getMonthValue());
    }
}

