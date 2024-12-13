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
        this.words = GameDatabase.loadWordGuessingData(language, difficulty);
        Collections.shuffle(words);
    }

    @Override
    public int playGame() {
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirmExit(frame)) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });

        JOptionPane.showMessageDialog(frame, "In this game, you will be given a clue. Your task is to guess the word.\nYou will have a limited time for each question.\nPress 'Cancel' to exit the game and return to the main menu.");

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 120 : 75;
        int maxQuestions = Math.min(10 + (difficulty * 5), words.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(frame, "Time's up!");
                break;
            }

            String[] wordPair = words.removeFirst();
            String userAnswer = getUserInput(frame, "PALMA, Guess the word: " + wordPair[0]);

            if (userAnswer == null) {
                if (confirmExit(frame)) {
                    frame.dispose();
                    return 0;
                }
                continue;
            }

            attempts++;
            if (userAnswer.equalsIgnoreCase(wordPair[1])) {
                JOptionPane.showMessageDialog(frame, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! Correct: " + wordPair[1]);
            }
        }

        frame.dispose();

        UserDatabase.updateStats(getUserID(), "WGPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }

    private String getUserInput(JFrame frame, String prompt) {
        return JOptionPane.showInputDialog(frame, prompt);
    }

    
    private boolean confirmExit(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to exit the game?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
}
