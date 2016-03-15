package ru.javawebinar.topjava.web.meal;


import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController extends AbstractUserMealController {

    public List<UserMeal> getAll() {
        return super.getAll(LoggedUser.id());
    }

    public UserMeal save(UserMeal userMeal) {
        return super.create(userMeal, LoggedUser.id());
    }

    public void delete(int id) {
        super.delete(id, LoggedUser.id());
    }

    public UserMeal get(int id) {
        return super.get(id, LoggedUser.id());
    }
}
