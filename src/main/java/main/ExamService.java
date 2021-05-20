package main;

import handler.ServiceHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamService {
    private int port = 8888;
    private static org.apache.log4j.Logger logger = Logger.getLogger(ExamService.class);
    ServerSocket serverSocket;
    private void init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error("serverSocket 启动失败");
            logger.error(e);
        }
    }
    public static void main(String args[]) {
        ExamService examService = new ExamService();
        examService.init();
        ExecutorService executorService = Executors.newCachedThreadPool();
        while(true) {
            try {
                Socket accept = examService.serverSocket.accept();
                executorService.execute(() -> {
                    Socket currentAccept = accept;
                    logger.info(currentAccept.getLocalAddress() + "已连接");
                    ServiceHandler serviceHandler = new ServiceHandler();
                    serviceHandler.setSocket(currentAccept);
                    serviceHandler.init();
                    serviceHandler.scheduleCommand();
                });
            } catch (IOException e) {
                logger.error("无法与客户端连接");
                logger.error(e);
            }
        }
    }
}
