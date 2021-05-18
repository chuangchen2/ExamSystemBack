package controller;

import domain.User;
import exception.LoginFailureException;
import util.DataBaseUtil;
import util.FactoryUtil;
import java.sql.*;

public class loginController {

    public User SocketLogin(String username, String password) throws LoginFailureException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userid, username, userpassword, groupid FROM exam_user WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (password.equals(resultSet.getString(3))) {
                    return FactoryUtil.userFactory(resultSet);
                }
                else {
                    throw new LoginFailureException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoginFailureException();
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
        throw new LoginFailureException();
    }
}
