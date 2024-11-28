package Game;

public class User {
    private String userID;
    private String userName;
    private String password;
    private String email;
    private String birthday;
    private String currentCourse;
    private String achievements;

    public User(String userID, String userName, String password, String email, String birthday) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.currentCourse = null;
        this.achievements = "";
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

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public void displayUser() {
        System.out.println("User ID: " + userID);
        System.out.println("User Name: " + userName);
        System.out.println("Email: " + email);
        System.out.println("Birthday: " + birthday);
        System.out.println("Current Course: " + currentCourse);
        System.out.println("Achievements: " + achievements);
    }
}
