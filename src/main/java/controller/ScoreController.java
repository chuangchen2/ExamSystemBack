package controller;

import dao.ScoreDao;
import handler.IOHandler;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.SQLException;

public class ScoreController {
    private Logger logger = Logger.getLogger(ScoreController.class);
    private IOHandler ioHandler = null;

    private ScoreController(){}

    public ScoreController(Socket socket) {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    public boolean insertScore(String userID, String courseID, String score) {
        ScoreDao scoreDao = new ScoreDao();
        try {
            if (scoreDao.insertScore(userID, courseID, score) != null) {
                ioHandler.writeln("IS1");
                return true;
            } else {
                ioHandler.writeln("IS2");
                return false;
            }
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("IS2");
        }
        return false;
    }
}
