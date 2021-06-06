package dao;

import domain.Score;
import org.apache.log4j.Logger;
import util.DataBaseUtil;
import util.FactoryUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScoreDao extends AbstractDao{
    static {
        logger = Logger.getLogger(ScoreDao.class);
    }

    public Score insertScore (String userID, String courseID, String score) throws SQLException {
        try {
            connection = DataBaseUtil.getConnection();
            statement = connection.prepareStatement("INSERT INTO exam_score (scoreid, userid," +
                    "courseid, score) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, null);
            statement.setString(2, userID);
            statement.setString(3, courseID);
            statement.setString(4, score);
            statement.executeUpdate();
            resultSet= statement.getGeneratedKeys();
            String generatedKey = "";
            if (resultSet.next()) {
                generatedKey = resultSet.getString(1);
            }
            if (generatedKey.equals("")) {
                throw new SQLException();
            } else {
                return FactoryUtil.scoreFactory(generatedKey, userID, courseID, score);
            }
        } finally {
            DataBaseUtil.release(connection, statement,resultSet);
        }
    }
}
