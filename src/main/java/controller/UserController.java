package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import domain.User;
import exception.LoginFailException;
import handler.IOHandler;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    private IOHandler ioHandler = null;

    private UserController() {}

    public UserController(Socket socket) {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    public boolean socketGetCourses(User user) {
        UserDao userDao = new UserDao();
        try {
            List<Map<String, String>> courses = userDao.getCourses(user);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courses);
            ioHandler.writeln("GC1");
            ioHandler.writeln(json);
            return true;
        } catch (SQLException e) {
            ioHandler.writeln("GC2");
            logger.error(e);
        } catch (JsonProcessingException e) {
            ioHandler.writeln("GC2");
            logger.info(e);
        }
        return false;
    }

    public User socketLogin(String username, String password) throws LoginFailException {
        UserDao userDao = new UserDao();
        try {
            if (password.equals(userDao.getPassword(username))) {
                User user = userDao.getUser(username);
                Map<String, String> ret = new HashMap<>();
                ret.put("userID", user.getUserID());
                ret.put("userName", user.getUserName());
                ret.put("groupID", user.getGroupID());
                ret.put("groupName", user.getGroupName());
                ObjectMapper mapper = new ObjectMapper();
                String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ret);
                ioHandler.writeln("L1");
                ioHandler.writeln(s);
                logger.info(user + "????????????");
                return userDao.getUser(username);
            } else {
                ioHandler.writeln("L2");
                logger.info(username + "??????????????????");
            }
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("L4");
            throw new LoginFailException();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User socketRegister(String username, String password, String groupname) {
        UserDao userDao = new UserDao();
        User newUser = null;
        try {
            newUser = userDao.insertUser(username, password, groupname);
            if (newUser != null) {
                ioHandler.writeln("R1");
                ioHandler.writeln(newUser.getUserID());
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

    public boolean socketUpdatePassword(User user, String oldPassword, String newPassword) {
        UserDao userDao = new UserDao();
        try {
            if (user.getUserPassword().equals(oldPassword)) {
                if (userDao.updatePassword(user.getUserID(), newPassword)) {
                    ioHandler.writeln("UP1");
                } else {
                    ioHandler.writeln("UP2");
                }
            } else {
                ioHandler.writeln("UP2");
            }
        } catch (SQLException e) {
            ioHandler.writeln("UP3");
            logger.error(e);
        }
        return false;
    }
}
