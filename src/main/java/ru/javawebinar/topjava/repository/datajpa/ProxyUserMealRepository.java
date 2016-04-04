package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by alk on 02.04.16.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    List<UserMeal> findByUserIdOrderByDateTimeDesc(Integer userId);

    List<UserMeal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Override
    UserMeal save(UserMeal s);

    @Override
    UserMeal findOne(Integer integer);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal u WHERE u.id=:id")
    void delete(@Param("id") int id);
}