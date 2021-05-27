package dao;

import domain.Course;
import domain.User;
import util.DataBaseUtil;
import util.FactoryUtil;

import java.io.*;
import java.sql.SQLException;

public class CourseDao extends AbstractDao {

    public String getQuestionJsonString(Course course) throws IOException {
        String path = course.getPath();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            while ((temp = bufferedReader.readLine())  != null) {
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        }
    }

    public Course getCourse(String courseID) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT courseid, coursename, 'path' FROM exam_course WHERE courseid=?");
            statement.setString(1, courseID);
            resultSet = statement.executeQuery();
            Course course = FactoryUtil.courseFactory(resultSet);
            return course;
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }
}
