package dao;

import domain.User;
import util.DataBaseUtil;
import util.FactoryUtil;

import java.sql.*;

import org.apache.log4j.Logger;

public class UserDao {
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private static Logger logger = Logger.getLogger(UserDao.class);

    public UserDao() {
    }

    public User insertUser(String username, String password) {
        return insertUser(username, password, "1");
    }

    public User insertUser(String username, String password, String groupid) {
        try {
            connection = DataBaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO exam_user (userid, username, userpassword, groupid) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, groupid);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            String generatedKey = "";
            if (resultSet.next()) {
                generatedKey = resultSet.getString(1);
            }
            if (generatedKey.equals("")) {
                logger.error("创建用户失败");
                logger.error(new SQLException());
            } else {
                return FactoryUtil.userFactor(generatedKey, username, password, groupid);
            }
        } catch (SQLException e) {
            logger.error("创建用户失败");
            logger.error(e);
        }
        return null;
    }

    public String getPassword(String username) {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userpassword FROM exam_user WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            logger.error(e);
        }
        return "";
    }

    public User getUser(String username) {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userid, username, userpassword, groupid FROM exam_user WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            User user = FactoryUtil.userFactory(resultSet);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
        return null;
    }
}
