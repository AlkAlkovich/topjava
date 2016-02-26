package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.print(getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();

    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> summCalloriesByDay = mealList.stream().
                collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return  mealList.stream().
                filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime)).
                map(userMeal1 -> new UserMealWithExceed(userMeal1.getDateTime(),userMeal1.getDescription(),userMeal1.getCalories(),summCalloriesByDay.get(userMeal1.getDateTime().toLocalDate())>caloriesPerDay)).collect(Collectors.toList());
//
//  Map<LocalDate, Integer> mapCaloriesDay = new HashMap<>();
//        List<UserMealWithExceed> k = new ArrayList<>();
//        mealList.stream().forEach(userMeal1 -> {
//            LocalDate localDate=userMeal1.getDateTime().toLocalDate();
//            if(mapCaloriesDay.containsKey(localDate)){
//                mapCaloriesDay.put(localDate,mapCaloriesDay.get(localDate)+userMeal1.getCalories());
//            }else {
//                mapCaloriesDay.put(localDate,userMeal1.getCalories());
//            }
//        });
//        mealList.stream().forEach(userMeal -> {
//            int currentCalories = 0;
//            LocalDate localDate=userMeal.getDateTime().toLocalDate();
//            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
//                boolean exceed=mapCaloriesDay.get(localDate)>caloriesPerDay;
//                k.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed));
//
//            }
//        });


    }
}
