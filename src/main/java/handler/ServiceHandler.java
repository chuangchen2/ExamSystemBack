package handler;

import controller.CourseController;
import controller.GroupController;
import controller.ScoreController;
import controller.UserController;
import domain.User;
import exception.LoginFailException;
import org.apache.log4j.Logger;
import util.RegexUtil;

import java.io.IOException;
import java.net.Socket;
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
                //System.err.println(reuturnedUser);
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
                        returned = userController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), "5");
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

        else if (command.equals("getcourses") && user != null) {
            UserController userController = new UserController(socket);
            userController.socketGetCourses(user);
        }

        else if (Pattern.matches("^updatepassword.*", command) && logined) {
            Matcher updatePasswordMatcher = RegexUtil.getUpdatePasswordMatcher(command);
            UserController userController = new UserController(socket);
            if (updatePasswordMatcher.find()) {
                String oldPassword =  updatePasswordMatcher.group(1);
                String newPassword = updatePasswordMatcher.group(2);
                userController.socketUpdatePassword(user, oldPassword, newPassword);
            }
        }

        else if (Pattern.matches("^getquestions.*", command) && logined) {
            CourseController courseController = new CourseController(socket);
            Matcher questionsMatcher = RegexUtil.getQuestionsMatcher(command);
            if (questionsMatcher.find()) {
                courseController.getQuestions(questionsMatcher.group(1));
            }
        }

        else if (Pattern.matches("^postscore.*", command) && logined) {
            ScoreController scoreController = new ScoreController(socket);
            Matcher postScoreMatcher = RegexUtil.getPostScoreMatcher(command);
            if (postScoreMatcher.find()) {
                scoreController.insertScore(user.getUserID(), postScoreMatcher.group(1), postScoreMatcher.group(2));
            }
        }

        else if (logined && user.getGroupID().equals("1") && Pattern.matches("^newgroup.*", command)) {
            GroupController groupController = new GroupController(socket);
            Matcher newGroupMatcher = RegexUtil.getNewGroupMatcher(command);
            if (newGroupMatcher.find()) {
                groupController.newGroup(newGroupMatcher.group(1));
            }
        }

        else if (logined && user.getGroupID().equals("1") && Pattern.matches("^changegroupname.*", command)) {
            GroupController groupController = new GroupController(socket);
            Matcher changeGroupNameMatcher = RegexUtil.getchangeGroupNameMatcher(command);
            if (changeGroupNameMatcher.find()) {
                groupController.changeGroupName(changeGroupNameMatcher.group(1), changeGroupNameMatcher.group(2));
            }
        }

        else if (logined && user.getGroupID().equals("1") && Pattern.matches("^getusers.*", command)) {
            GroupController groupController = new GroupController(socket);
            Matcher getUsersMatcher = RegexUtil.getGetUsersMatcher(command);
            if (getUsersMatcher.find()) {
                groupController.getUsers(getUsersMatcher.group(1));
            }
        }

        else if (logined && user.getGroupID().equals("1") && Pattern.matches("^getgroups", command)) {
            GroupController groupController = new GroupController(socket);
            groupController.getGroups();
        }

        else {
            ioHandler.writeln("FE");
            logger.info(user + command + " 命令格式错误或权限不足");
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
                //System.err.println(command);
                logger.info("命令为" + command);
                stateMachine(command);
            } catch (IOException e) {
                //logger.info(e);
                logger.info(ioHandler.getOrgin().getLocalAddress() + " 连接已释放");
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
