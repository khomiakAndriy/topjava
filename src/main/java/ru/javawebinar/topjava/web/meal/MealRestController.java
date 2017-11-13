package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal create(Meal meal){
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id){
        return service.get(id, AuthorizedUser.id());
    }

    public void update(Meal meal){
        service.update(meal, AuthorizedUser.id());
    }

    public Collection<Meal> getAll(){
        return service.getAll(AuthorizedUser.id());
    }
}