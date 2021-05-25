package ru.sber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sber.dao.UserDao;
import ru.sber.model.User;
import ru.sber.model.UserForm;

import java.util.List;

@Controller
public class UserControllerAnnotationCfg {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ModelAndView getAllUsers(@RequestParam(value = "first_name", required = false) String firstName) {
        List<User> userList;
        if (firstName != null) {
            userList = userDao.findAllByFirstName(firstName);
        } else {
            userList = userDao.findAll();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("usersFromServer", userList);
        return modelAndView;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(UserForm form) {
        User user = User.builder()
                .userName(form.getFirstName())
                .lastName(form.getLastName())
                .build();
        userDao.save(user);
        return "redirect:/users";
    }
}
