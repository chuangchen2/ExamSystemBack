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

    public User insertUser(String username, String password) throws Exception{
        return insertUser(username, password, "1");
    }

    public User insertUser(String username, String password, String groupid) throws Exception{
        try {
            connection = DataBaseUtil.getConnection();
            statement= connection.prepareStatement("INSERT INTO exam_user (userid, username, userpassword, groupid) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, null);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, groupid);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            String generatedKey = "";
            if (resultSet.next()) {
                generatedKey = resultSet.getString(1);
            }
            if (generatedKey.equals("")) {
                throw new SQLException();
            } else {
                return FactoryUtil.userFactor(generatedKey, username, password, groupid);
            }
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }

    public String getPassword(String username) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userpassword FROM exam_user WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }

    public User getUser(String username) throws SQLException{
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userid, username, userpassword, groupid FROM exam_user WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            User user = FactoryUtil.userFactory(resultSet);
            return user;
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }
}
