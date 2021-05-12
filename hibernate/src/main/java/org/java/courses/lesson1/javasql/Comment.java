package org.java.courses.lesson1.javasql;

public class Comment {
    private int id;
    private int userId;
    private String text;

    public Comment(int id, int userId, String text) {
        this.id = id;
        this.userId = userId;
        this.text = text;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                '}';
    }
}
