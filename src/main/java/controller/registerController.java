package controller;

import dao.UserDao;
import domain.User;
import exception.RegisterFailException;

import java.sql.SQLException;

public class registerController {

    public User socketRegister(String username, String password, String groupname) {
        UserDao userDao = new UserDao();
        User newUser = null;
        try {
            newUser = userDao.insertUser(username, password, groupname);
            if (newUser != null) {
                return newUser;
            } else {
                throw new RegisterFailException();
            }
        } catch (Exception e) {
            throw new RegisterFailException(e.getClass().getName());
        }
    }
}
