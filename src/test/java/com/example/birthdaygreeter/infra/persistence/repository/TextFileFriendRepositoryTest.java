package com.example.birthdaygreeter.infra.persistence.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.fixture.entity.FriendEntityFixture;
import com.example.birthdaygreeter.infra.persistence.entity.FriendEntity;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class TextFileFriendRepositoryTest {

    @Autowired
    private TextFileFriendRepository textFileFriendRepository;

    @Value("${app.friends.textfile.path}")
    private String filePath;

    private final Faker faker = new Faker();

    @Before
    public void setUp() throws IOException {
        // Prepare the test file with fake data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < 10; i++) {
                FriendEntity friendEntity = FriendEntityFixture.random();
                bw.write(String.format("%s %s %s %s%n",
                        friendEntity.getLastName(),
                        friendEntity.getFirstName(),
                        friendEntity.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        friendEntity.getEmail()));
            }
        }

    }

    @Test
    public void findAll_ShouldReturnCorrectNumberOfFriends() {
        List<Friend> friends = textFileFriendRepository.findAll();
        assertEquals(10, friends.size());
    }

    @Test
    public void findFriendsWithBirthdateToday_ShouldReturnCorrectNumberOfFriends() throws IOException {
        LocalDate today = LocalDate.now();
        addFriendWithBirthdayAsToday();

        List<Friend> friends = textFileFriendRepository.findFriendsWithBirthdateToday(today);

        assertEquals(1, friends.size());
    }

    @Test
    public void findFriendByEmailNotIn_ShouldReturnCorrectNumberOfFriends() throws IOException {
        List<FriendEntity> friendWithBirthdayToday = List.of(
                addFriendWithBirthdayAsToday(),
                addFriendWithBirthdayAsToday()
        );

        List<String> emails = friendWithBirthdayToday.stream()
                .map(FriendEntity::getEmail)
                .toList();

        List<Friend> friends = textFileFriendRepository.findFriendByEmailNotIn(emails);
        assertEquals(10, friends.size());
    }

    private FriendEntity addFriendWithBirthdayAsToday() throws IOException {
        FriendEntity friendEntity = FriendEntityFixture.withBirthdayToday();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.format("%s %s %s %s%n",
                    friendEntity.getLastName(),
                    friendEntity.getFirstName(),
                    friendEntity.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                    friendEntity.getEmail()));
        }
        return friendEntity;
    }

}