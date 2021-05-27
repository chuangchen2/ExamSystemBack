package domain;

import java.util.List;

public class Question {
    private String type;
    private List<List<String>> lists;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<String>> getLists() {
        return lists;
    }

    public void setLists(List<List<String>> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "Question{" +
                "type='" + type + '\'' +
                ", item=" + lists +
                '}';
    }
}
