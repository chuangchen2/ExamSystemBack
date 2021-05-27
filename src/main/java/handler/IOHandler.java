package handler;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class IOHandler {
    private static Map<Socket, IOHandler> socketPool = new HashMap<>();
    private static Logger logger = Logger.getLogger(IOHandler.class);
    private Socket orgin;
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private InputStream inputStream;
    private DataInputStream dataInputStream;
    private BufferedReader bufferedReader;

    private IOHandler() {
    }

    private IOHandler(Socket socket) {
        try {
            orgin = socket;
            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IOHandler getIOhandler(Socket origin) {
        if (socketPool.get(origin) != null) {
            return socketPool.get(origin);
        }
        else {
            IOHandler ioHandler = new IOHandler(origin);
            socketPool.put(origin, ioHandler);
            return ioHandler;
        }
    }

    public void writeln(String string) {
        try {
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e);
            System.err.println("1");
        }
    }

    public String readln() throws IOException {
        String readString = "";
        readString = dataInputStream.readUTF();
        return readString;
    }

    public void release() {
        try {
            bufferedReader.close();
            dataInputStream.close();
            inputStream.close();
            dataOutputStream.close();
            outputStream.close();
            socketPool.remove(this.orgin);
        } catch (IOException ex) {
            System.err.println("3");
            logger.error(ex);
        }
    }
}
