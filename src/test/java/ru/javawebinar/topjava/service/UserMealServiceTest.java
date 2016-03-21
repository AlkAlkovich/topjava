package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by alk on 20.03.16.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService userMealService;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        MATCHER.assertEquals(MEAL6,userMealService.get(100007, 100001));
    }

    @Test
    public void testDelete() throws Exception {
        userMealService.delete(100007, 100001);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL5,MEAL4),userMealService.getAll(100001));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<UserMeal>userMealCollection=userMealService.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.MAY, 31),
                100001);
        MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL4,MEAL5,MEAL6),
                userMealCollection);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<UserMeal>userMealCollection=userMealService.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 14, 0),
                100001);
        MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL4,MEAL5),
                userMealCollection
                );
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL3,MEAL2,MEAL1),
                userMealService.getAll(100000));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal userMeal = userMealService.get(100002, 100000);
        userMeal.setDescription("Update");
        userMealService.update(userMeal, 100000);
        MATCHER.assertEquals(getUpdated(),userMealService.update(userMeal, 100000));
    }

    @Test
    public void testSave() throws Exception {
        UserMeal userMeal = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 21, 0), "saved", 300);
        userMealService.save(userMeal, 100001);
        MATCHER.assertEquals(getSaved(),userMealService.save(userMeal, 100001));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteOthers() throws Exception {
        userMealService.delete(100003,100001);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateOthers() throws Exception {
        userMealService.update(getUpdated(),100001);
    }

    @Test(expected = NotFoundException.class)
    public void testGetOthers(){
        userMealService.get(100002,100001);
    }
}