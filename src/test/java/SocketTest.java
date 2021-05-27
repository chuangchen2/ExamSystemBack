import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.ExamService;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.List;

public class SocketTest {

    @Test
    public void test1() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF("login " + "root " + "123456");
        dataOutputStream.flush();
        String s = dataInputStream.readUTF();
        String json = dataInputStream.readUTF();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> maps = mapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
        String groupName = maps.get("groupName");
        System.out.println(maps);
        socket.close();
    }
}
