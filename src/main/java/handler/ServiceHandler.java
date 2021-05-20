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
    private static Logger logger = Logger.getLogger(ServiceHandler.class);
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
            loginController loginController = new loginController();
            User reuturnedUser = null;
            try {
                if(loginMatcher.find()) {
                    reuturnedUser = loginController.SocketLogin(loginMatcher.group(1), loginMatcher.group(2));
                }
                setUser(reuturnedUser);
                System.err.println(reuturnedUser);
                ioHandler.writeln("L1 " + user.getUserID());
                logined = true;
            } catch (LoginFailException e) {
                logger.error(e);
                ioHandler.writeln("L2");
            } catch (IllegalStateException illegalStateException) {
                logger.error("登录命令格式错误");
                logger.error(illegalStateException);
            }
        }

        if (Pattern.matches("^register.*", command)) {
            Matcher registerMatcher = RegexUtil.getRegisterMatcher(command);
            registerController registerController = new registerController();
            try {
                if (registerMatcher.find()) {
                    User returned = null;
                    if (registerMatcher.group(3).equals("")) {
                        returned = registerController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), "1");
                    } else {
                        returned = registerController.socketRegister(registerMatcher.group(1), registerMatcher.group(2), registerMatcher.group(3));
                    }
                    ioHandler.writeln("R1" + returned.getUserID());
                }
            } catch (RegisterFailException e) {
                logger.error(e);
                ioHandler.writeln("R2");
            } catch (IllegalStateException e) {
                logger.error("注册命令格式错误");
                logger.error(e);
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
