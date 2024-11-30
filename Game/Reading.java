package Game;

import javax.swing.JOptionPane;
import java.util.Queue;
import java.util.LinkedList;

public class Reading extends GameBase {
    private Queue<String[]> sentences;

    public Reading(int difficulty, String language) {
        super(difficulty, language);
        this.sentences = DataLoader.loadReadingData(language, difficulty);
    }

    @Override
    public int playGame() {
        if (sentences.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxQuestions = Math.min(10 + (difficulty * 5), sentences.size());

        for (int i = 0; i < maxQuestions; i++) {
            String[] sentencePair = sentences.poll();
            String userAnswer = getUserInput("Translate: " + sentencePair[0]);

            attempts++;
            if (userAnswer != null && userAnswer.equalsIgnoreCase(sentencePair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! Correct: " + sentencePair[1]);
            }
        }

        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
}
