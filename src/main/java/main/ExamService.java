package main;

import handler.ServiceHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamService {
    private int port = 8888;
    ServerSocket serverSocket;
    private void init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
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
                    //System.err.println("2");
                    ServiceHandler serviceHandler = new ServiceHandler();
                    //System.err.println("4");
                    serviceHandler.setSocket(currentAccept);
                    serviceHandler.init();
                    //System.err.println("3");
                    serviceHandler.scheduleCommand();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
