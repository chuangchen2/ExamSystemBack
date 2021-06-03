import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CourseDao;
import dao.UserDao;
import domain.Course;
import domain.Question;
import domain.User;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CourseTest {

    @Test
    public void test1() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao userDao = new UserDao();
        User user = new User();
        user.setGroupID("1");
        user.setUserID("1");
        List<Map<String, String>> courses = userDao.getCourses(user);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(courses));
    }

    @Test
    public void test2() throws IOException {
        CourseDao courseDao = new CourseDao();
        Course course = new Course();
        course.setPath("storage/jisuanjiwangluo.json");
        String questionJsonString = courseDao.getQuestionJsonString(course);
        ObjectMapper mapper = new ObjectMapper();
        List<Question> questions = mapper.readValue(questionJsonString, new TypeReference<List<Question>>() {
        });
        System.out.println(questions);
    }
}
