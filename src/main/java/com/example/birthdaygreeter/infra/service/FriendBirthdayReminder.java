package com.example.birthdaygreeter.infra.service;

import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.domain.friend.FriendRepository;
import com.example.birthdaygreeter.application.service.BirthdayReminder;
import com.example.birthdaygreeter.application.service.SendEmail;
import com.example.birthdaygreeter.application.service.SendSms;
import com.example.birthdaygreeter.infra.internal.InvalidDatasourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FriendBirthdayReminder implements BirthdayReminder {

    private final Logger LOG = LoggerFactory.getLogger(FriendBirthdayReminder.class);

    private final FriendRepository friendRepository;
    private final SendEmail sendEmail;
    private final SendSms sendSms;

    @Autowired
    public FriendBirthdayReminder(@Value("${app.repository.type}") String repositoryType,
                                  @Qualifier("sqlite") FriendRepository sqliteRepository,
                                  @Qualifier("textfile") FriendRepository textFileRepository,
                                  SendEmail sendEmailNote,
                                  SendSms sendSmsNote) {
        this.sendEmail = sendEmailNote;
        this.sendSms = sendSmsNote;
        if (repositoryType.equalsIgnoreCase("sqlite")) {
            this.friendRepository = sqliteRepository;
        } else if (repositoryType.equalsIgnoreCase("textfile")){
            this.friendRepository = textFileRepository;
        } else
            throw new InvalidDatasourceException("repository type not supported. valid values are: sqlite, textfile");
    }

    @Override
    public void handle(LocalDate localDate) {
        List<Friend> friendsWithBirthdayToday = friendRepository.findFriendsWithBirthdateToday(localDate);

        if(friendsWithBirthdayToday.isEmpty()) {
            LOG.info("nobody has a birthday today.");
            return;
        }

        sendHappyBirthdayGreeter(friendsWithBirthdayToday);

        sendHappyBirthdayReminder(friendsWithBirthdayToday);

    }

    private void sendHappyBirthdayGreeter(List<Friend> to) {
        sendEmail.happyBirthdayGreeter(to);
        sendSms.happyBirthdayGreeter(to);
    }

    private void sendHappyBirthdayReminder(List<Friend> friendsWithBirthdayToday) {

        List<String> birthdayEmails = friendsWithBirthdayToday.stream().map(Friend::getEmail).toList();

        List<Friend> friendToSendReminder = friendRepository.findFriendByEmailNotIn(birthdayEmails);

        friendsWithBirthdayToday.forEach(birhthdayFriend -> {
            sendEmail.happyBirthdayReminder(friendToSendReminder, birhthdayFriend);
            sendSms.happyBirthdayReminder(friendToSendReminder, birhthdayFriend);
        });
    }

}
