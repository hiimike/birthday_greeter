package com.example.birthdaygreeter.infra.persistence.repository;

import com.example.birthdaygreeter.infra.persistence.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository()
public interface FriendJPARepository extends JpaRepository<FriendEntity, String> {
    @Query("SELECT f FROM FriendEntity f WHERE MONTH(f.dateOfBirth) = MONTH(:today) AND function('strftime', '%d', f.dateOfBirth) IN(:days)")
    List<FriendEntity> findFriendsWithBirthdateToday(LocalDate today, List<Integer> days);

    List<FriendEntity> findFriendByEmailNotIn(List<String> ids);
}
