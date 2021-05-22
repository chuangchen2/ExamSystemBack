package controller;

import dao.UserDao;
import domain.User;
import exception.RegisterFailException;
import handler.IOHandler;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class registerController {
    private IOHandler ioHandler = null;
    private Logger logger = Logger.getLogger(registerController.class);

    private registerController(){}

    public registerController(Socket socket) {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    public User socketRegister(String username, String password, String groupname) {
        UserDao userDao = new UserDao();
        User newUser = null;
        try {
            newUser = userDao.insertUser(username, password, groupname);
            if (newUser != null) {
                ioHandler.writeln("R1" + newUser.getUserID());
                return newUser;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            ioHandler.writeln("R2");
        } catch (Exception e) {
            logger.error(e);
            ioHandler.writeln("R3");
        }
        return null;
    }
}
