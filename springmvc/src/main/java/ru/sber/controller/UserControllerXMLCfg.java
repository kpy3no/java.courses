package ru.sber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ru.sber.dao.UserDao;
import ru.sber.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class UserControllerXMLCfg implements Controller {

    @Autowired
    private UserDao userDao;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        List<User> userList =  userDao.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("usersFromServer" , userList);
        return modelAndView;
    }
}
