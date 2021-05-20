package controller;

import dao.UserDao;
import domain.User;
import exception.RegisterFailException;

public class registerController {

    public User socketRegister(String username, String password, String groupname) {
        UserDao userDao = new UserDao();
        User newUser = userDao.insertUser(username, password, groupname);
        if (newUser != null) {
           return newUser;
        } else {
            throw new RegisterFailException();
        }
    }
}
