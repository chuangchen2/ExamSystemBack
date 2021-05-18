package util;

import domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FactoryUtil {

    public static User userFactory(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserID(resultSet.getString(1));
        user.setUserName(resultSet.getString(2));
        user.setUserPassword(resultSet.getString(3));
        user.setGroupID(resultSet.getString(4));
        return user;
    }
}
