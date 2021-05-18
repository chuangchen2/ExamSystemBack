package handler;

import controller.loginController;
import domain.User;
import exception.LoginFailureException;
import org.apache.log4j.Logger;
import util.RegexUtil;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceHandler {
    private Socket socket;
    private User user;
    private IOHandler ioHandler;
    private static Logger logger = Logger.getLogger(ServiceHandler.class);

    public ServiceHandler() {
        super();
    }

    public void init() {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    private void stateMachine(String command) {
        if (Pattern.matches("^login.*", command)) {
            System.err.println(command);
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
            } catch (LoginFailureException e) {
                logger.error(e);
                ioHandler.writeln("L2");
            } catch (IllegalStateException illegalStateException) {
                logger.error(illegalStateException);
            }
        }
    }

    public void scheduleCommand() {
        String command = "";
        while (true) {
            command = ioHandler.readln();
            if(command.equals("")) {
                continue;
            }
            System.err.println(command);
            stateMachine(command);
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
