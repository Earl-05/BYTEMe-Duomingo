package Game;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Stack;

public class WordGuessing extends GameBase {
    private Stack<String[]> words;

    public WordGuessing(int difficulty, String language) {
        super(difficulty, language);
        Stack<String[]> wordList = GameDatabase.loadWordGuessingData(language, difficulty);
        Collections.shuffle(wordList); // Shuffle questions
        this.words = new Stack<>();
        this.words.addAll(wordList);
    }

    @Override
    public int playGame() {
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }
        
        JFrame frame = new JFrame("WORD GUESSING GAME");
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
        int maxTime = difficulty == 0 ? 120 : 75; // Beginner: 120 sec, Intermediate: 75 sec
        int maxQuestions = Math.min(10 + (difficulty * 5), words.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(null, "Time's up!");
                break;
            }

            String[] wordPair = words.pop();
            String userAnswer = getUserInput("Guess the word: " + wordPair[0]);
            
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
            if (userAnswer != null && userAnswer.equalsIgnoreCase(wordPair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! Correct: " + wordPair[1]);
            }
        }

        // Update user stats
        UserDatabase.updateStats(getUserID(), "WGPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
}
