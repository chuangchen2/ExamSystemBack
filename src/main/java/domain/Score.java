package domain;

public class Score {
    private String scoreID;
    private String userID;
    private String courseID;
    private String score;

    public String getScoreID() {
        return scoreID;
    }

    public void setScoreID(String scoreID) {
        this.scoreID = scoreID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
