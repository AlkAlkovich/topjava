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

    List<UserMeal> findByUserAndDateTimeBetweenOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Modifying
    @Query("SELECT m FROM UserMeal m "+
            "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<UserMeal>getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("userId") int userId);
    @Override
    UserMeal save(UserMeal s);

    @Override
    UserMeal findOne(Integer integer);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal u WHERE u.id=:id")
    void delete(@Param("id") int id);
}