package com.example.birthdaygreeter.fixture.entity;

import com.example.birthdaygreeter.infra.persistence.entity.FriendEntity;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FriendEntityFixture {
    private static Faker faker = new Faker();

    public static FriendEntity random() {
        return FriendEntity.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .dateOfBirth(faker.date().birthday().toLocalDateTime().toLocalDate())
                .build();
    }

    public static List<FriendEntity> randomQuantity(int quantity) {
        List<FriendEntity> result = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            result.add(random());
        }
        return result;
    }

    public static FriendEntity withBirthdayToday() {
        FriendEntity result = random();
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
