package com.example.birthdaygreeter.application.service;

import com.example.birthdaygreeter.domain.friend.Friend;

import java.util.List;

public interface SendEmail {

    void happyBirthdayGreeter(List<Friend> recipients);

    void happyBirthdayReminder(List<Friend> recipients, Friend birthdayFriend);
}
