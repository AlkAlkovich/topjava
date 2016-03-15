package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alk on 15.03.16.
 */
public class UserUtils {
    public static final List<User> USER_LIST= Arrays.asList(
            new User(1,"Test1","Test1@mail.ru","12345",Role.ROLE_USER),
            new User(2,"Test2","Test2@mail.ru","12345",Role.ROLE_USER));
}
