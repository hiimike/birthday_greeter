package com.example.birthdaygreeter.infra.persistence.repository;

import com.example.birthdaygreeter.domain.friend.Friend;
import com.example.birthdaygreeter.domain.friend.FriendRepository;
import com.example.birthdaygreeter.infra.persistence.entity.FriendEntity;
import com.example.birthdaygreeter.util.DateUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("sqlite")
public class SQLiteFriendRepository implements FriendRepository {

    private final FriendJPARepository friendJPARepository;


    public SQLiteFriendRepository(FriendJPARepository friendJPARepository) {
        this.friendJPARepository = friendJPARepository;
    }

    @Override
    public List<Friend> findFriendsWithBirthdateToday(LocalDate today) {
        List<Integer> days = DateUtils.getAdjustDays(today);
        return toDomain(friendJPARepository.findFriendsWithBirthdateToday(today, days));
    }

    @Override
    public List<Friend> findFriendByEmailNotIn(List<String> emails) {
        return toDomain(friendJPARepository.findFriendByEmailNotIn(emails));
    }

    @Override
    public List<Friend> findAll() {
        return toDomain(friendJPARepository.findAll());
    }

    private List<Friend> toDomain(List<FriendEntity> JPAEntities) {
        return JPAEntities.stream().map(entity ->
                Friend.builder()
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .email(entity.getEmail())
                        .dateOfBirth(entity.getDateOfBirth())
                        .build()
        ).toList();

    }
}
