package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static Pattern loginPattern;
    static {
        loginPattern = Pattern.compile("login\\s([^\\s]*)\\s([^\\s]*)");
    }

    public static Matcher getLoginMatcher(String string) {
        return loginPattern.matcher(string);
    }
}
