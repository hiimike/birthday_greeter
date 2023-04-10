package com.example.birthdaygreeter.domain.friend;

import java.time.LocalDate;
import java.util.List;

public interface FriendRepository {

    List<Friend> findFriendsWithBirthdateToday(LocalDate today);

    List<Friend> findFriendByEmailNotIn(List<String> ids);

    List<Friend> findAll();
}
