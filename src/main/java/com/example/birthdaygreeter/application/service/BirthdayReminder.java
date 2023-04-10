package com.example.birthdaygreeter.application.service;

import java.time.LocalDate;

public interface BirthdayReminder {

    void handle(LocalDate localDate);
}
