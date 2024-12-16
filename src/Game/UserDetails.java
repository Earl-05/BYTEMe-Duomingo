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
    private int SWPlayed;
    private int WAWon;
    private int SWWon;
    private int RWon;
    private int totalScore;


    public UserDetails() {
    }


	public UserDetails(String userID, String userName, String password, String email, String birthday,
			String currentCourse, List<String> achievements, String mainLanguage, int gamesPlayed, int wAPlayed,
			int rPlayed, int sWPlayed, int wAWon, int sWWon, int rWon, int totalScore) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.birthday = birthday;
		this.currentCourse = currentCourse;
		this.achievements = achievements;
		this.mainLanguage = mainLanguage;
		this.gamesPlayed = gamesPlayed;
		WAPlayed = wAPlayed;
		RPlayed = rPlayed;
		SWPlayed = sWPlayed;
		WAWon = wAWon;
		SWWon = sWWon;
		RWon = rWon;
		this.totalScore = totalScore;
	}


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


	public void setWAPlayed(int wAPlayed) {
		WAPlayed = wAPlayed;
	}


	public int getRPlayed() {
		return RPlayed;
	}


	public void setRPlayed(int rPlayed) {
		RPlayed = rPlayed;
	}


	public int getSWPlayed() {
		return SWPlayed;
	}


	public void setSWPlayed(int sWPlayed) {
		SWPlayed = sWPlayed;
	}


	public int getWAWon() {
		return WAWon;
	}


	public void setWAWon(int wAWon) {
		WAWon = wAWon;
	}


	public int getSWWon() {
		return SWWon;
	}


	public void setSWWon(int sWWon) {
		SWWon = sWWon;
	}


	public int getRWon() {
		return RWon;
	}


	public void setRWon(int rWon) {
		RWon = rWon;
	}


	public int getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
    
}