package com.example.birthdaygreeter.infra.persistence.repository;

import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.fixture.entity.FriendEntityFixture;
import com.example.birthdaygreeter.infra.persistence.entity.FriendEntity;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class SQLiteFriendRepositoryTest {

    private final Faker faker = new Faker();
    private FriendJPARepository friendJPARepository;
    private SQLiteFriendRepository sqLiteFriendRepository;

    @Before
    public void setUp() {
        friendJPARepository = Mockito.mock(FriendJPARepository.class);
        sqLiteFriendRepository = new SQLiteFriendRepository(friendJPARepository);
    }

    @Test
    public void findAll_ShouldReturnCorrectNumberOfFriends() {
        int quantity = faker.number().numberBetween(1, 10);
        List<FriendEntity> friendEntities = FriendEntityFixture.randomQuantity(quantity);
        Mockito.when(friendJPARepository.findAll()).thenReturn(friendEntities);

        List<Friend> friends = sqLiteFriendRepository.findAll();

        assertEquals(quantity, friends.size());
        Mockito.verify(friendJPARepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findFriendsWithBirthdateToday_ShouldReturnCorrectNumberOfFriends() {
        LocalDate today = LocalDate.now();
        List<FriendEntity> friendEntities = List.of(FriendEntityFixture.withBirthdayToday());
        Mockito.when(friendJPARepository.findFriendsWithBirthdateToday(today))
                .thenReturn(friendEntities);

        List<Friend> friends = sqLiteFriendRepository.findFriendsWithBirthdateToday(today);

        assertEquals(1, friends.size());
        assertEquals(friendEntities.get(0).getFirstName(), friends.get(0).getFirstName());
        assertEquals(friendEntities.get(0).getLastName(), friends.get(0).getLastName());
        assertEquals(friendEntities.get(0).getEmail(), friends.get(0).getEmail());
        assertEquals(friendEntities.get(0).getDateOfBirth(), friends.get(0).getDateOfBirth());
        Mockito.verify(friendJPARepository, Mockito.times(1)).findFriendsWithBirthdateToday(eq(today));
    }

    @Test
    public void findFriendByEmailNotIn_ShouldReturnCorrectNumberOfFriends() {
        int quantity = faker.number().numberBetween(2, 10);
        List<FriendEntity> friendEntities = FriendEntityFixture.randomQuantity(quantity);
        List<String> emails = List.of(friendEntities.get(0).getEmail());
        friendEntities.remove(0);
        Mockito.when(friendJPARepository.findFriendByEmailNotIn(any()))
                .thenReturn(friendEntities);

        List<Friend> friends = sqLiteFriendRepository.findFriendByEmailNotIn(emails);

        assertEquals(quantity - 1, friends.size());
        Mockito.verify(friendJPARepository, Mockito.times(1)).findFriendByEmailNotIn(eq(emails));
    }
}
