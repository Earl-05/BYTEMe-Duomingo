package Game;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public abstract class GameBase implements GameInterface {
    private int difficulty;
    private String language;
    private String userID;

    public GameBase(int difficulty, String language) {
        this.difficulty = difficulty;
        this.language = language;
    }

    @Override
    public abstract int playGame();

    // Getter and Setter for difficulty
    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    // Getter and Setter for language
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // Getter and Setter for userID
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Public method for user input
    public String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

    // Public method to calculate score
    public int calculateScore(int attempts, long timeTaken, int maxQuestions) {
        int timeBonus = difficulty == 0 ? 300 : 200;
        int maxTime = timeBonus * maxQuestions;

        if (timeTaken > maxTime) {
            JOptionPane.showMessageDialog(null, "Time's up! Total score is calculated with penalties.");
        }

        return Math.max(0, (maxQuestions * 10) - (int) (timeTaken / 2));
    }

    // Public method to shuffle list
    public <T> void shuffleList(List<T> list) {
        Collections.shuffle(list);
    }
}
