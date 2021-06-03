package dao;

import domain.User;
import util.DataBaseUtil;
import util.FactoryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class UserDao extends AbstractDao {
    static {
        logger = Logger.getLogger(UserDao.class);
    }

    public UserDao() {
    }

    public User insertUser(String username, String password) throws Exception{
        return insertUser(username, password, "1");
    }

    public List<Map<String, String>> getCourses(User user) throws SQLException {
        List<Map<String, String>> ret = new ArrayList<>();
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT coursename, score FROM " +
                    "(SELECT gc.`groupid`, gc.`courseid`, c.`coursename` FROM groupcourse gc, exam_course c WHERE gc.courseid=c.courseid AND gc.groupid=?) c2 " +
                    "LEFT JOIN (SELECT courseid, score FROM exam_score s WHERE s.userid=?) u " +
                    "ON c2.courseid=u.courseid");
            statement.setString(1, user.getGroupID());
            statement.setString(2, user.getUserID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> item = new HashMap<>();
                item.put("coursename", resultSet.getString(1));
                if (resultSet.getString(2) != null) {
                    item.put("finished", "true");
                    item.put("score", resultSet.getString(2));
                } else {
                    item.put("finished", "false");
                    item.put("score", "-1");
                }
                ret.add(item);
            }
            return ret;
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }

    public boolean updatePassword(String userID, String newPassword) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("UPDATE exam_user SET userpassword=? WHERE userid=?");
            statement.setString(1, newPassword);
            statement.setString(2, userID);
            int i = statement.executeUpdate();
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }

    public User insertUser(String username, String password, String groupid) throws Exception {
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
                return FactoryUtil.userFactory(generatedKey, username, password, groupid);
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

    public User getUser(String username) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userid, username, userpassword, groupid FROM exam_user WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            User user = FactoryUtil.userFactory(resultSet);
            GroupDao groupDao = new GroupDao();
            String groupName = groupDao.getGroupName(user.getGroupID());
            user.setGroupName(groupName);
            return user;
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }
}
