package Game;

import java.util.List;
import java.util.ArrayList;

public class UserDetails {
    private String userID;
    private String userName;
    private String password;
    private String email;
    private String birthday;
    private String currentCourse;
    private List<String> achievements;
    private String mainLanguage;
    private int gamesWon;
    private int WAPlayed;
    private int RPlayed;
    private int SWPlayed;
    private int WAWon;
    private int SWWon;
    private int RWon;
    


    public UserDetails() {
    	this.achievements = new ArrayList<>();
    }


	public UserDetails(String userID, String userName, String password, String email, String birthday,
			String currentCourse, List<String> achievements, String mainLanguage, int gamesWon, int wAPlayed,
			int rPlayed, int sWPlayed, int wAWon, int sWWon, int rWon) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.birthday = birthday;
		this.currentCourse = currentCourse;
		this.achievements = achievements != null ? achievements : new ArrayList<>();
		this.mainLanguage = mainLanguage;
		this.gamesWon = gamesWon;
		WAPlayed = wAPlayed;
		RPlayed = rPlayed;
		SWPlayed = sWPlayed;
		WAWon = wAWon;
		SWWon = sWWon;
		RWon = rWon;
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
		this.achievements = achievements != null ? achievements : new ArrayList<>();
	}


	public String getMainLanguage() {
		return mainLanguage;
	}


	public void setMainLanguage(String mainLanguage) {
		this.mainLanguage = mainLanguage;
	}


	public int getGamesWon() {
		return gamesWon;
	}


	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
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
	
	public void addAchievement(String achievement) {
		if (!achievements.contains(achievement)) {
			achievements.add(achievement);
		}
	}
	
}