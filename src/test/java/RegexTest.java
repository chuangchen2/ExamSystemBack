import org.junit.Test;
import util.RegexUtil;

import java.util.regex.Matcher;

public class RegexTest {

    @Test
    public void test1(){
        Matcher matcher = RegexUtil.getLoginMatcher("login root 123456");
        while(matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
