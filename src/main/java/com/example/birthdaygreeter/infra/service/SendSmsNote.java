package com.example.birthdaygreeter.infra.service;

import com.example.birthdaygreeter.application.service.SendSms;
import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.domain.sms.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SendSmsNote implements SendSms {

    private final Logger LOG = LoggerFactory.getLogger(SendSmsNote.class);
    @Override
    public void happyBirthdayGreeter(List<Friend> recipients) {
        recipients.forEach(friend ->
                {
                    Sms sms = Sms.builder()
                            .subject("Happy Birthday!")
                            .body("Happy Birthday, dear " + friend.getFirstName() + "!")
                            .build();
                    LOG.info("sending happy birthday greeter sms: {}", sms);
                }
        );
    }

    @Override
    public void happyBirthdayReminder(List<Friend> recipients, Friend birthdayFriend) {
        recipients.forEach(friend ->
                {
                    Sms sms = Sms.builder()
                            .subject("Birthday Reminder")
                            .body("Dear " + friend.getFirstName() + ",\n" +
                                    "Today is " + birthdayFriend.getFullName() + "'s birthday.\n" +
                                    "Don't forget to send him a message !")
                            .build();
                    LOG.info("sending happy birthday reminder sms: {}", sms);
                }
        );
    }
}
