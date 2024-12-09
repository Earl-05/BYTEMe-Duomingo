package Game;

import java.util.List;

public class UserDetails {
    private String userID;
    private String userName;
    private String password;
    private String email;
    private String birthday;
    private String currentCourse;
    private List<String> achievements;
    private String mainLanguage;
    private int gamesPlayed;
    private int WAPlayed;
    private int RPlayed;
    private int WGPlayed;
   
    public UserDetails() {
    }

    public UserDetails(String userID, String userName, String password, String email, String birthday,
            String currentCourse, List<String> achievements, String mainLanguage, int gamesPlayed, int wAPlayed,
            int rPlayed, int wGPlayed) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.currentCourse = currentCourse;
        this.achievements = achievements;
        this.mainLanguage = mainLanguage;
        this.gamesPlayed = gamesPlayed;
        this.WAPlayed = wAPlayed;
        this.RPlayed = rPlayed;
        this.WGPlayed = wGPlayed;
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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWAPlayed() {
        return WAPlayed;
    }

    public void setWAPlayed(int WAPlayed) {
        this.WAPlayed = WAPlayed;
    }

    public int getRPlayed() {
        return RPlayed;
    }

    public void setRPlayed(int RPlayed) {
        this.RPlayed = RPlayed;
    }

    public int getWGPlayed() {
        return WGPlayed;
    }

    public void setWGPlayed(int WGPlayed) {
        this.WGPlayed = WGPlayed;
    }
}
