package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;

public class WordAssociation extends GameBase {
    private LinkedList<String[]> wordPairs;

    public WordAssociation(int difficulty, String language) {
        super(difficulty, language);
        this.wordPairs = GameDatabase.loadWordAssociationData(language, difficulty);
        Collections.shuffle(wordPairs); // Shuffle questions
    }

    @Override
    public int playGame() {
        if (wordPairs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        JFrame frame = new JFrame("WORD ASSOCIATION GAME");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to exit the game?",
                        "Exit Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    System.exit(0); // Terminate the program
                }
            }
        });

        frame.setVisible(true);

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 60 : 45; // Beginner: 60 sec, Intermediate: 45 sec
        int maxQuestions = Math.min(10 + (difficulty * 5), wordPairs.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(frame, "Time's up!");
                break;
            }

            String[] pair = wordPairs.removeFirst();
            String userAnswer = getUserInput(frame, "Translate: " + pair[0]);

            if (userAnswer == null) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to exit the game?",
                        "Exit Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    System.exit(0); // Terminate the program
                }
                continue;
            }

            attempts++;
            if (userAnswer.equalsIgnoreCase(pair[1])) {
                JOptionPane.showMessageDialog(frame, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! Correct: " + pair[1]);
            }
        }

        frame.dispose(); // Close the game window

        // Update user stats
        UserDatabase.updateStats(getUserID(), "WAPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }

    private String getUserInput(JFrame frame, String prompt) {
        return JOptionPane.showInputDialog(frame, prompt);
    }
}
