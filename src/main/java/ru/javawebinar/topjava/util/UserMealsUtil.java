package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //create map with day's user meal
        Map<LocalDate, ArrayList<UserMeal>> dateUserMeal = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            if (!dateUserMeal.containsKey(userMeal.getDateTime().toLocalDate())) {
                ArrayList<UserMeal> list = new ArrayList<>();
                list.add(userMeal);
                dateUserMeal.put(userMeal.getDateTime().toLocalDate(), list);
            } else {
                dateUserMeal.get(userMeal.getDateTime().toLocalDate()).add(userMeal);
            }
        }

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        for (Map.Entry<LocalDate, ArrayList<UserMeal>> entry : dateUserMeal.entrySet()) {
            int dayCalories = 0;
            ArrayList<UserMeal> userMeals = entry.getValue();
            for (UserMeal um : userMeals) {
                dayCalories += um.getCalories();
            }
            if (dayCalories > caloriesPerDay) {
                for (UserMeal um : userMeals) {
                    userMealWithExceeds.add(new UserMealWithExceed(um, true));
                }
            } else {
                for (UserMeal um : userMeals) {
                    userMealWithExceeds.add(new UserMealWithExceed(um, false));
                }
            }
        }
        return userMealWithExceeds
                .stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
