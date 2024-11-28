package Game;

import java.util.List;

public class User {
    private String userID;
    private String userName;
    private String password;
    private String email;
    private String birthday;
    private String currentCourse;
    private List<String> achievements;
    private String mainLanguage;

    public User() {}

    public User(String userID, String userName, String password, String email, String birthday, String currentCourse,
                String mainLanguage, List<String> achievements) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.currentCourse = currentCourse;
        this.mainLanguage = mainLanguage;
        this.achievements = achievements;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(String currentCourse) {
        this.currentCourse = currentCourse;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }

    public String getMainLanguage() {
        return mainLanguage;
    }

    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }
}
