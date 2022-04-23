package model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String username;
    private String password;
    private int isYouBlog;

    public int getIsYouBlog() {
        return isYouBlog;
    }

    public void setIsYouBlog(int isYouBlog) {
        this.isYouBlog = isYouBlog;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
