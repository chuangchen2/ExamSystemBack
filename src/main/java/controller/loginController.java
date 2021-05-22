package controller;

import dao.UserDao;
import domain.User;
import exception.LoginFailException;
import handler.IOHandler;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.SQLException;

public class loginController {
    private Logger logger = Logger.getLogger(loginController.class);
    private IOHandler ioHandler = null;

    private loginController() {}

    public loginController(Socket socket) {
        ioHandler = IOHandler.getIOhandler(socket);
    }
    public User SocketLogin(String username, String password) throws LoginFailException {
        UserDao userDao = new UserDao();
        try {
            if (password.equals(userDao.getPassword(username))) {
                User user = userDao.getUser(username);
                ioHandler.writeln("L1 " + user.getUserID());
                logger.info(user + "登录成功");
                return userDao.getUser(username);
            } else {
                ioHandler.writeln("L2");
                logger.info(username + "尝试登录失败");
            }
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("L4");
            throw new LoginFailException();
        }
        return null;
    }
}
