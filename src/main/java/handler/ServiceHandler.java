package handler;

import controller.loginController;
import controller.registerController;
import domain.User;
import exception.LoginFailException;
import exception.RegisterFailException;
import org.apache.log4j.Logger;
import util.RegexUtil;

import java.io.EOFException;
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
            loginController loginController = new loginController(socket);
            User reuturnedUser = null;
            try {
                if(loginMatcher.find()) {
                    reuturnedUser = loginController.SocketLogin(loginMatcher.group(1), loginMatcher.group(2));
                }
                setUser(reuturnedUser);
                System.err.println(reuturnedUser);
                logined = true;
            } catch (IllegalStateException illegalStateException) {
                ioHandler.writeln("L3");
                logger.info("登录命令格式错误");
                logger.info(illegalStateException);
            } catch (LoginFailException e) {
            }
        }

        if (Pattern.matches("^register.*", command)) {
            Matcher registerMatcher = RegexUtil.getRegisterMatcher(command);
            registerController registerController = new registerController(socket);
            try {
                if (registerMatcher.find()) {
                    User returned = null;
                    if (registerMatcher.group(3).equals("")) {
                        returned = registerController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), "1");
                    } else {
                        returned = registerController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), registerMatcher.group(3));
                    }
                }
            } catch (IllegalStateException e) {
                logger.info("注册命令格式错误");
                logger.info(e);
            }
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
            } catch (EOFException e) {
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
