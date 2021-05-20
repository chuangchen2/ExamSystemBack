package controller;

import dao.UserDao;
import domain.User;
import exception.LoginFailException;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class loginController {
    private Logger logger = Logger.getLogger(loginController.class);

    public User SocketLogin(String username, String password) throws LoginFailException {
        UserDao userDao = new UserDao();
        try {
            if (password.equals(userDao.getPassword(username))) {
               return userDao.getUser(username);
            } else {
                throw new LoginFailException();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new LoginFailException();
        }
    }
}
