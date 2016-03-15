package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alk on 15.03.16.
 */

public abstract class AbstractUserMealController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMealService service;

    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll");
        return service.getAll(userId).stream().collect(Collectors.toList());
    }

    public UserMeal get(int id, int userId) {
        LOG.info("get " + id);
        return service.get(id, userId);
    }

    public UserMeal create(UserMeal userMeal, int userId) {
        userMeal.setId(Integer.parseInt(null));
        LOG.info("create " + userMeal);
        return service.save(userMeal, userId);
    }

    public void delete(int id, int userId) {
        LOG.info("delete " + id);
        service.delete(id, userId);
    }
}
