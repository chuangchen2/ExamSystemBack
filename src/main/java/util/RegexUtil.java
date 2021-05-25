package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static Pattern loginPattern;
    public static Pattern registerPattern;
    public static Pattern updatePasswordPattern;
    static {
        loginPattern = Pattern.compile("login\\s([^\\s]*)\\s([^\\s]*)");
        registerPattern = Pattern.compile("register\\s([^\\s]*)\\s([^\\s]*)\\s?([^\\s]*)?");
        updatePasswordPattern = Pattern.compile("updatepassword\\s([^\\s]*)\\s([^\\s]*)");
    }

    public static Matcher getLoginMatcher(String string) {
        return loginPattern.matcher(string);
    }
    
    public static Matcher getRegisterMatcher(String string) {
        return registerPattern.matcher(string);
    }
    
    public static Matcher getUpdatePasswordMatcher(String string) {
        return updatePasswordPattern.matcher(string);
    }
}
