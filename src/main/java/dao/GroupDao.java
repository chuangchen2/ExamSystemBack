package dao;

import org.apache.log4j.Logger;
import util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDao {
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private static Logger logger = Logger.getLogger(GroupDao.class);

    public GroupDao() {
    }

    public String getGroupName(String groupID) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("SELECT groupname FROM exam_group WHERE groupid=?");
            statement.setString(1, groupID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } finally {
            DataBaseUtil.release(connection, statement, resultSet);
        }
        return "";
    }
}
