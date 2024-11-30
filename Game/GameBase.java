package Game;

import javax.swing.JOptionPane;

public abstract class GameBase implements GameInterface {
    protected int difficulty;
    protected String language;

    public GameBase(int difficulty, String language) {
        this.difficulty = difficulty;
        this.language = language;
    }

    protected int calculateScore(int attempts, long timeTaken, int maxQuestions) {
        return (maxQuestions * 10) - (attempts * 2) + (int) (100 / timeTaken) + (difficulty * 5);
    }

    protected String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }
}
