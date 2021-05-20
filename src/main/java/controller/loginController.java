package controller;

import dao.UserDao;
import domain.User;
import exception.LoginFailException;

public class loginController {

    public User SocketLogin(String username, String password) throws LoginFailException {
        UserDao userDao = new UserDao();
        if (password.equals(userDao.getPassword(username))) {
           return userDao.getUser(username);
        } else {
            throw new LoginFailException();
        }
    }
}
