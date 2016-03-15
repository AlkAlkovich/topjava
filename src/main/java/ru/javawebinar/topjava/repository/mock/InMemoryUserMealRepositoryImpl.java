package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer,Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1,new ConcurrentHashMap<>());
        UserMealsUtil.MEAL_LIST.forEach(userMeal -> save(userMeal,1));
    }

    @Override
    public UserMeal save(UserMeal userMeal,int userId) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.get(userId).put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public UserMeal save(UserMeal userMeal) {

        return null;
    }

    @Override
    public void delete(int id,int userId) {
        repository.get(userId).remove(id);
    }

    @Override
    public UserMeal get(int id,int userId) {
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.get(userId).values().
                stream().sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime())).
                collect(Collectors.toList());
    }
}

