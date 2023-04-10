package com.example.birthdaygreeter.infra.service;

import com.example.birthdaygreeter.application.service.SendEmail;
import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.domain.mail.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SendEmailNote implements SendEmail {

    private final Logger LOG = LoggerFactory.getLogger(SendEmailNote.class);
    @Override
    public void happyBirthdayGreeter(List<Friend> recipients) {
        recipients.forEach(friend ->
                {
                    Email email = Email.builder()
                            .to(friend.getEmail())
                            .subject("Happy Birthday!")
                            .body("Happy Birthday, dear " + friend.getFirstName() + "!")
                            .build();
                    LOG.info("sending happy birthday greeter email: {}", email);
                }
        );
    }

    @Override
    public void happyBirthdayReminder(List<Friend> recipients, Friend birthdayFriend) {
        recipients.forEach(friend ->
                {
                    Email email = Email.builder()
                            .to(friend.getEmail())
                            .subject("Birthday Reminder")
                            .body("Dear " + friend.getFirstName() + ",\n" +
                                    "Today is " + birthdayFriend.getFullName() + "'s birthday.\n" +
                                    "Don't forget to send him a message !")
                            .build();
                    LOG.info("sending happy birthday reminder email: {}", email);
                }
        );
    }
}
