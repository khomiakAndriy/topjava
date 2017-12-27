package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;


    @RequestMapping
    public String meals(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExceeded(
                mealService.getAll(AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        mealService.delete(Integer.valueOf(id), AuthorizedUser.id());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        Meal meal = mealService.get(
                Integer.valueOf(request.getParameter("id")),
                AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping
    public String updateOnCreate(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            mealService.create(meal, AuthorizedUser.id());
        } else {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            mealService.update(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(HttpServletRequest request, Model model){
        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDate.parse(request.getParameter("startDate")),
                LocalTime.parse(request.getParameter("startTime")));
        LocalDateTime endDateTime = LocalDateTime.of(
                LocalDate.parse(request.getParameter("endDate")),
                LocalTime.parse(request.getParameter("endTime")));

        List<Meal> meals = mealService.getBetweenDateTimes(startDateTime, endDateTime, AuthorizedUser.id());
        model.addAttribute("meals", MealsUtil.getWithExceeded(meals,
                AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }
}
