package Game;

import javax.swing.*;
import java.util.Collections;
import java.util.LinkedList;

public class WordAssociation extends GameBase {
    private LinkedList<String[]> wordPairs;

    public WordAssociation(int difficulty, String language) {
        super(difficulty, language);
        this.wordPairs = DataLoader.loadWordAssociationData(language, difficulty);
        Collections.shuffle(wordPairs); // Shuffle questions
    }

    @Override
    public int playGame() {
        if (wordPairs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 60 : 45; // Beginner: 60 sec, Intermediate: 45 sec
        int maxQuestions = Math.min(10 + (difficulty * 5), wordPairs.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(null, "Time's up!");
                break;
            }

            String[] pair = wordPairs.removeFirst();
            String userAnswer = getUserInput("Translate: " + pair[0]);

            attempts++;
            if (userAnswer != null && userAnswer.equalsIgnoreCase(pair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! Correct: " + pair[1]);
            }
        }

        // Update user stats
        UserDatabase.updateStats(getUserID(), "WAPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
}
