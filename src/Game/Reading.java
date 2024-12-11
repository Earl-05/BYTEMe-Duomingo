package Game;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Reading extends GameBase {
    private Queue<String[]> sentences;

    public Reading(int difficulty, String language) {
        super(difficulty, language);
        LinkedList<String[]> sentenceList = GameDatabase.loadReadingData(language, difficulty);
        Collections.shuffle(sentenceList); // Shuffle questions
        this.sentences = new LinkedList<>(sentenceList);
    }

    @Override
    public int playGame() {
        if (sentences.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }
        
        JFrame frame = new JFrame("READING GAME");
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
        int maxTime = difficulty == 0 ? 90 : 60; // Beginner: 90 sec, Intermediate: 60 sec
        int maxQuestions = Math.min(10 + (difficulty * 5), sentences.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(null, "Time's up!");
                break;
            }

            String[] sentencePair = sentences.poll();
            String userAnswer = getUserInput("Translate: " + sentencePair[0]);
            
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
            if (userAnswer != null && userAnswer.equalsIgnoreCase(sentencePair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! Correct: " + sentencePair[1]);
            }
        }

        // Update user stats
        UserDatabase.updateStats(getUserID(), "RPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
}
