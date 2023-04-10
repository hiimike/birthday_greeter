package com.example.birthdaygreeter.infra.service;

import com.example.birthdaygreeter.application.service.SendEmail;
import com.example.birthdaygreeter.application.service.SendSms;
import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.domain.friend.FriendRepository;
import com.example.birthdaygreeter.fixture.domain.FriendFixture;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FriendBirthdayReminderTest {

    private final Faker faker = new Faker();

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private SendEmail sendEmail;

    @Mock
    private SendSms sendSms;

    private FriendBirthdayReminder friendBirthdayReminder;

    @BeforeEach
    public void setUp() {
        friendBirthdayReminder = new FriendBirthdayReminder(
                faker.options().option("sqlite", "textfile"),
                friendRepository,
                friendRepository,
                sendEmail,
                sendSms
        );
    }

    @Test
    public void handle_ShouldSendBirthdayGreeterAndBirthdayReminder() {
        List<Friend> friendsWithBirthdayToday = Collections.singletonList(FriendFixture.withBirthdayToday());
        List<Friend> friendsWithoutBirthdayToday = Arrays.asList(FriendFixture.random(), FriendFixture.random());

        Mockito.when(friendRepository.findFriendsWithBirthdateToday(any(LocalDate.class)))
                .thenReturn(friendsWithBirthdayToday);
        Mockito.when(friendRepository.findFriendByEmailNotIn(anyList()))
                .thenReturn(friendsWithoutBirthdayToday);

        friendBirthdayReminder.handle(LocalDate.now());

        Mockito.verify(sendEmail, times(1)).happyBirthdayGreeter(friendsWithBirthdayToday);
        Mockito.verify(sendSms, times(1)).happyBirthdayGreeter(friendsWithBirthdayToday);

        Mockito.verify(sendEmail, times(1)).happyBirthdayReminder(friendsWithoutBirthdayToday, friendsWithBirthdayToday.get(0));
        Mockito.verify(sendSms, times(1)).happyBirthdayReminder(friendsWithoutBirthdayToday, friendsWithBirthdayToday.get(0));
    }

    @Test
    public void handle_ShouldNotSendBirthdayGreeterAndBirthdayReminder() {
        List<Friend> friendsWithoutBirthdayToday = Arrays.asList(FriendFixture.random(), FriendFixture.random());

        Mockito.when(friendRepository.findFriendsWithBirthdateToday(any(LocalDate.class)))
                .thenReturn(List.of());

        friendBirthdayReminder.handle(LocalDate.now());

        Mockito.verify(sendEmail, never()).happyBirthdayGreeter(any());
        Mockito.verify(sendSms, never()).happyBirthdayGreeter(any());

        Mockito.verify(sendEmail, never()).happyBirthdayReminder(eq(friendsWithoutBirthdayToday), any());
        Mockito.verify(sendSms, never()).happyBirthdayReminder(eq(friendsWithoutBirthdayToday), any());
    }
}
