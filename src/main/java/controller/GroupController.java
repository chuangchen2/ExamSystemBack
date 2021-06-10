package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.GroupDao;
import domain.Group;
import domain.User;
import handler.IOHandler;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class GroupController {
    private static Logger logger = Logger.getLogger(GroupController.class);
    private IOHandler ioHandler = null;

    private GroupController(){}

    public GroupController(Socket socket) {
        ioHandler = IOHandler.getIOhandler(socket);
    }

    public void getUsers(String groupID) {
        GroupDao groupDao = new GroupDao();
        try {
            List<User> userList = groupDao.queryUsers(groupID);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userList);
            ioHandler.writeln("GU1");
            ioHandler.writeln(json);
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("GU2");
        } catch (JsonProcessingException e) {
            logger.error(e);
            ioHandler.writeln("GU2");
        }
    }

    public void changeGroupName(String oldGroupID, String newGroupName) {
        GroupDao groupDao = new GroupDao();
        try {
            int i = groupDao.updateGroupName(oldGroupID, newGroupName);
            if (i != 0) {
                ioHandler.writeln("CG1");
            } else {
                ioHandler.writeln("CG2");
            }
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("CG2");
        }
    }

    public void newGroup(String groupoName)  {
        GroupDao groupDao = new GroupDao();
        try {
            int i = groupDao.insertGroup(groupoName);
            if (i != 0) {
                ioHandler.writeln("IG1");
            } else {
                ioHandler.writeln("IG2");
            }
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("IG2");
        }
    }

    public void getGroups() {
        GroupDao groupDao = new GroupDao();
        try {
            List<Group> groupList = groupDao.queryGroups();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groupList);
            ioHandler.writeln("GG1");
            ioHandler.writeln(json);
        } catch (SQLException e) {
            logger.error(e);
            ioHandler.writeln("GG1");
        } catch (JsonProcessingException e) {
            logger.error(e);
            ioHandler.writeln("GG1");
        }
    }
}
