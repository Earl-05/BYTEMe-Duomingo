package Game;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public abstract class GameBase {
    protected int difficulty;
    protected String language;
    protected String userID;

    public GameBase(int difficulty, String language) {
        this.difficulty = difficulty;
        this.language = language;
        this.userID = userID;
    }

    public abstract int playGame();

    protected String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }

    protected int calculateScore(int attempts, long timeTaken, int maxQuestions) {
        int timeBonus = difficulty == 0 ? 300 : 200;
        int maxTime = timeBonus * maxQuestions;

        if (timeTaken > maxTime) {
            JOptionPane.showMessageDialog(null, "Time's up! Total score is calculated with penalties.");
        }

        int score = Math.max(0, (maxQuestions * 10) - (int) (timeTaken / 2));
        return score;
    }

    protected <T> void shuffleList(List<T> list) {
        Collections.shuffle(list);
    }
    
    protected String getUserID() {
        return this.userID;
    }
}
