package com.example.birthdaygreeter.fixture.domain;

import com.example.birthdaygreeter.domain.friend.Friend;
import net.datafaker.Faker;

import java.time.LocalDate;

public class FriendFixture {
    private static Faker faker = new Faker();

    public static Friend random() {
        return Friend.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .dateOfBirth(faker.date().birthday().toLocalDateTime().toLocalDate())
                .build();
    }

    public static Friend withBirthdayToday() {
        Friend result = random();
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(
                faker.number().numberBetween(1960, 2000),
                today.getMonth(),
                today.getDayOfMonth()
        );
        result.setDateOfBirth(birthday);
        return result;
    }
}
