package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
@Transactional(readOnly = true)
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    private ProxyUserMealRepository proxyUserMealRepository;
    @Autowired
    private ProxyUserRepository proxyUserRepository;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        userMeal.setUser(proxyUserRepository.findOne(userId));
        if (userMeal.isNew()) {
            return proxyUserMealRepository.save(userMeal);
        } else {
            return get(userMeal.getId(), userId) == null
                    ? null
                    : proxyUserMealRepository.save(userMeal);
        }

    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        if (get(id, userId)!=null) {
            proxyUserMealRepository.delete(id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = proxyUserMealRepository.findOne(id);
        if (userMeal.getUser().getId() == userId) {
            return proxyUserMealRepository.findOne(id);
        } else {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxyUserMealRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    @Transactional
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
//        return proxyUserMealRepository.findByUserAndDateTimeBetweenOrderByDateTimeDesc(startDate, endDate, userId);
        return proxyUserMealRepository.getBetween(startDate, endDate, userId);
    }
}
