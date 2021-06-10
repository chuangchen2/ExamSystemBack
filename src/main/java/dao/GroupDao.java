package dao;

import domain.Group;
import domain.User;
import org.apache.log4j.Logger;
import util.DataBaseUtil;
import util.FactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends AbstractDao{
    private static Logger logger = Logger.getLogger(GroupDao.class);

    public GroupDao() {
    }

    public String getGroupName(String groupID) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT groupname FROM exam_group WHERE groupid=?");
            statement.setString(1, groupID);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
        return "";
    }

    public int insertGroup(String groupoName) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("INSERT INTO exam_group (groupid, groupname) VALUES (?, ?)");
            statement.setString(1, null);
            statement.setString(2, groupoName);
            int i = statement.executeUpdate();
            return i;
        } finally {
            DataBaseUtil.release(connection, statement, null);
        }
    }

    public int updateGroupName(String oldGroupID, String newGroupName) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("UPDATE exam_group SET groupname=? WHERE groupid=?");
            statement.setString(1, newGroupName);
            statement.setString(2, oldGroupID);
            int i = statement.executeUpdate();
            return i;
        } finally {
            DataBaseUtil.release(connection, statement, null);
        }
    }

    public List<User> queryUsers(String groupID) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT userid, username FROM exam_user WHERE groupid=?");
            statement.setString(1, groupID);
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(FactoryUtil.userFactory(resultSet.getString(1), resultSet.getString(2), null, groupID));
            }
            return users;
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }

    public List<Group> queryGroups() throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT groupid, groupname FROM exam_group");
            resultSet = statement.executeQuery();
            List<Group> groupList = new ArrayList<>();
            while (resultSet.next()) {
                groupList.add(FactoryUtil.groupFactory(resultSet.getString(1), resultSet.getString(2)));
            }
            return groupList;
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
    }
}
