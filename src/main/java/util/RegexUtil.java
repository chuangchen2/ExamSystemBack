package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    private static Pattern loginPattern;
    private static Pattern registerPattern;
    private static Pattern updatePasswordPattern;
    private static Pattern questionsPattern;
    private static Pattern postScorePattern;
    private static Pattern newGroupPattern;
    private static Pattern changeGroupNamePattern;
    private static Pattern getUsersPattern;
    static {
        loginPattern = Pattern.compile("login\\s([^\\s]*)\\s([^\\s]*)");
        registerPattern = Pattern.compile("register\\s([^\\s]*)\\s([^\\s]*)\\s?([^\\s]*)?");
        updatePasswordPattern = Pattern.compile("updatepassword\\s([^\\s]*)\\s([^\\s]*)");
        questionsPattern = Pattern.compile("getquestion\\s([^\\s]*)");
        postScorePattern = Pattern.compile("postscore\\s([^\\s]*)\\s([^\\s]*)");
        newGroupPattern = Pattern.compile("newgroup\\s([^\\s]*)");
        changeGroupNamePattern = Pattern.compile("changegoupname\\s([^\\s]*)\\s([^\\s]*)");
        getUsersPattern = Pattern.compile("getusers\\s([^\\s]*)");
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

    public static Matcher getPostScoreMatcher(String command) {
        return postScorePattern.matcher(command);
    }

    public static Matcher getNewGroupMatcher(String command) { return newGroupPattern.matcher(command);
    }

    public static Matcher getchangeGroupNameMatcher(String command) {
        return changeGroupNamePattern.matcher(command);
    }

    public static Matcher getGetUsersMatcher(String command) {
        return getUsersPattern.matcher(command);
    }

}
