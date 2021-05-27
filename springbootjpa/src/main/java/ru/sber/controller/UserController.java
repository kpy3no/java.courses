package ru.sber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sber.entity.Car;
import ru.sber.entity.User;
import ru.sber.entity.UserForm;
import ru.sber.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ModelAndView getAllUsers() {

        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");


        List<Car> cars = userList.stream().flatMap(s -> s.getCars().stream())
                .collect(Collectors.toList());

        modelAndView.addObject("usersFromServer", userList);
        modelAndView.addObject("carsFromServer", cars);
        return modelAndView;
    }


    @RequestMapping(path = "/users/test", method = RequestMethod.GET)
    public ModelAndView getAllUsersBy() {

        List<User> userList = userService.test();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");


        List<Car> cars = userList.stream().flatMap(s -> s.getCars().stream())
                .collect(Collectors.toList());

        modelAndView.addObject("usersFromServer", userList);
        modelAndView.addObject("carsFromServer", cars);
        return modelAndView;
    }



    @RequestMapping(path = "users", method = RequestMethod.POST)
    public String addUser(UserForm userForm) {
        User newUser = User.from(userForm);
        userService.save(newUser);
        return "redirect:/users";
    }
}
