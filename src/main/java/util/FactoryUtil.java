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

    public static User userFactor(String userid, String username, String password, String groupid) {
        User user = new User();
        user.setUserID(userid);
        user.setUserName(username);
        user.setUserPassword(password);
        user.setGroupID(groupid);
        return user;
    }
}
