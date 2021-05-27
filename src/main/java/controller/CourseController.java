package controller;

import dao.CourseDao;
import domain.Course;
import handler.IOHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class CourseController {
    private Logger logger = Logger.getLogger(CourseController.class);
    private IOHandler ioHandler = null;

    private CourseController() {}

    public CourseController(Socket socket) {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    public boolean getQuestions(String courseID) {
        CourseDao courseDao = new CourseDao();
        try {
            Course course = courseDao.getCourse(courseID);
            String questionJsonString = courseDao.getQuestionJsonString(course);
            ioHandler.writeln("GQ1");
            ioHandler.writeln(questionJsonString);
            return true;
        } catch (SQLException | IOException e) {
            ioHandler.writeln("GQ2");
            logger.error(e);
            return false;
        }
    }
}
