package Game;

import javax.swing.JOptionPane;
import java.util.Stack;

public class WordGuessing extends GameBase {
    private Stack<String[]> words;

    public WordGuessing(int difficulty, String language) {
        super(difficulty, language);
        this.words = DataLoader.loadWordGuessingData(language, difficulty);
    }

    @Override
    public int playGame() {
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxQuestions = Math.min(10 + (difficulty * 5), words.size());

        for (int i = 0; i < maxQuestions; i++) {
            String[] wordPair = words.pop();
            String userAnswer = getUserInput("Guess the word: " + wordPair[0]);

            attempts++;
            if (userAnswer != null && userAnswer.equalsIgnoreCase(wordPair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! Correct: " + wordPair[1]);
            }
        }

        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
}
