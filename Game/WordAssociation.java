package Game;

import javax.swing.JOptionPane;
import java.util.LinkedList;

public class WordAssociation extends GameBase {
    private LinkedList<String[]> wordPairs;

    public WordAssociation(int difficulty, String language) {
        super(difficulty, language);
        this.wordPairs = DataLoader.loadWordAssociationData(language, difficulty);
    }

    @Override
    public int playGame() {
        if (wordPairs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxQuestions = Math.min(10 + (difficulty * 5), wordPairs.size());

        for (int i = 0; i < maxQuestions; i++) {
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

        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
}
