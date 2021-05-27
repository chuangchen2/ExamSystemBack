package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    private static Pattern loginPattern;
    private static Pattern registerPattern;
    private static Pattern updatePasswordPattern;
    private static Pattern questionsPattern;
    static {
        loginPattern = Pattern.compile("login\\s([^\\s]*)\\s([^\\s]*)");
        registerPattern = Pattern.compile("register\\s([^\\s]*)\\s([^\\s]*)\\s?([^\\s]*)?");
        updatePasswordPattern = Pattern.compile("updatepassword\\s([^\\s]*)\\s([^\\s]*)");
        questionsPattern = Pattern.compile("getquestion\\s([^\\s]*)");
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

    public static Matcher getQuestionsMatcher(String string) {
        return questionsPattern.matcher(string);
    }
}
