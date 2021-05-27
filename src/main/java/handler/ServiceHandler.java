package handler;

import controller.CourseController;
import controller.UserController;
import dao.CourseDao;
import domain.User;
import exception.LoginFailException;
import org.apache.log4j.Logger;
import util.RegexUtil;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceHandler {
    private Socket socket;
    private User user;
    private IOHandler ioHandler;
    private static final Logger logger = Logger.getLogger(ServiceHandler.class);
    private boolean logined;

    public ServiceHandler() {
        super();
    }

    public void init() {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    private void stateMachine(String command) {
        if (Pattern.matches("^login.*", command)) {
            Matcher loginMatcher = RegexUtil.getLoginMatcher(command);
            UserController userController = new UserController(socket);
            User reuturnedUser = null;
            try {
                if(loginMatcher.find()) {
                    reuturnedUser = userController.socketLogin(loginMatcher.group(1), loginMatcher.group(2));
                }
                setUser(reuturnedUser);
                System.err.println(reuturnedUser);
                logined = true;
            } catch (IllegalStateException illegalStateException) {
                ioHandler.writeln("FE");
                logger.info("登录命令格式错误");
                logger.info(illegalStateException);
            } catch (LoginFailException e) {
            }
        }

        else if (Pattern.matches("^register.*", command)) {
            Matcher registerMatcher = RegexUtil.getRegisterMatcher(command);
            UserController userController = new UserController(socket);
            try {
                if (registerMatcher.find()) {
                    User returned = null;
                    if (registerMatcher.group(3).equals("")) {
                        returned = userController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), "1");
                    } else {
                        returned = userController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), registerMatcher.group(3));
                    }
                }
            } catch (IllegalStateException e) {
                ioHandler.writeln("FE");
                logger.info("注册命令格式错误");
                logger.info(e);
            }
        }

        else if(command.equals("getcourses") && user != null) {
            UserController userController = new UserController(socket);
            userController.socketGetCourses(user);
        }

        else if(Pattern.matches("^updatepassword.*", command) && logined) {
            Matcher updatePasswordMatcher = RegexUtil.getUpdatePasswordMatcher(command);
            UserController userController = new UserController(socket);
            if (updatePasswordMatcher.find()) {
                String oldPassword =  updatePasswordMatcher.group(1);
                String newPassword = updatePasswordMatcher.group(2);
                userController.socketUpdatePassword(user, oldPassword, newPassword);
            }
        }

        else if(Pattern.matches("^getquestions.*", command) && logined) {
            CourseController courseController = new CourseController(socket);
            Matcher questionsMatcher = RegexUtil.getQuestionsMatcher(command);
            if (questionsMatcher.find()) {
                courseController.getQuestions(questionsMatcher.group(1));
            }
        }

        else {
            ioHandler.writeln("FE");
            logger.info("命令格式错误");
        }
    }

    public void scheduleCommand() {
        String command = "";
        while (true) {
            try {
                command = ioHandler.readln();
                if(command.equals("")) {
                    continue;
                }
                System.err.println(command);
                stateMachine(command);
            } catch (IOException e) {
                ioHandler.release();
                break;
            }
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
