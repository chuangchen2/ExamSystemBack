package dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbstractDao {
    protected Connection connection = null;
    protected PreparedStatement statement = null;
    protected ResultSet resultSet = null;
    protected static Logger logger = null;
}
