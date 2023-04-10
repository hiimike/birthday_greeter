package com.example.birthdaygreeter.infra.task;

import com.example.birthdaygreeter.application.service.BirthdayReminder;
import com.example.birthdaygreeter.infra.service.FriendBirthdayReminder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DailyReminder {

    private final BirthdayReminder birthdayReminder;

    public DailyReminder(FriendBirthdayReminder friendBirthdayReminder) {
        this.birthdayReminder = friendBirthdayReminder;
    }

    //@Scheduled(cron = "0 0 6 * * ?")
    @Scheduled(initialDelay = 1000, fixedDelay = 60000)
    public void dailyReminderJob() {
        LocalDate localDate = LocalDate.now();
        birthdayReminder.handle(localDate);
    }
}
