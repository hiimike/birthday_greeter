package com.example.birthdaygreeter.infra.persistence.repository;

import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.domain.friend.FriendRepository;
import com.example.birthdaygreeter.infra.internal.InvalidDateOfBirthException;
import com.example.birthdaygreeter.util.DateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository("textfile")
public class TextFileFriendRepository implements FriendRepository {

    @Value("${app.friends.textfile.path}")
    private String FILE_PATH;

    @Override
    public List<Friend> findFriendsWithBirthdateToday(LocalDate today) {
        return findAll().stream()
                .filter(friend -> friend.getDateOfBirth().getMonth() == today.getMonth()
                        && friend.getDateOfBirth().getDayOfMonth() == today.getDayOfMonth()
                )
                .toList();
    }

    @Override
    public List<Friend> findFriendByEmailNotIn(List<String> emails) {
        return findAll().stream()
                .filter(friend -> !emails.contains(friend.getEmail()))
                .toList();
    }

    @Override
    public List<Friend> findAll() {
        List<Friend> friends = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s+");
                String lastName = fields[0];
                String firstName = fields[1];
                if(!DateValidator.isValidDate(fields[2]))
                    throw new InvalidDateOfBirthException("invalid date format. expected yyyy/MM/dd" + line);
                LocalDate dateOfBirth = LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                String email = fields[3];

                Friend friend = new Friend(lastName, firstName, dateOfBirth, email);
                friends.add(friend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return friends;
    }
}
