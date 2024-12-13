package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;

public class WordAssociation extends GameBase {
    private LinkedList<String[]> wordPairs;
	private int maxTries;

    public WordAssociation(int difficulty, String language) {
        super(difficulty, language);
        this.wordPairs = GameDatabase.loadWordAssociationData(language, difficulty);
        Collections.shuffle(wordPairs);
        this.maxTries = difficulty == 0? 5 : 3;
    }

    @Override
    public int playGame() {
        if (wordPairs.isEmpty()) {
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
                }
            }
        });

        JOptionPane.showMessageDialog(frame, "In this game, you will be given a word. Your task is to translate it correctly.\nYou will have 5 tries (Beginner) and 3 tries (Intermediate) and a time limit of 60 seconds (Beginner) and 45 seconds (Intermediate).\nPress 'Cancel' to exit the game and return to the main menu.", "INSTRUCTION",JOptionPane.INFORMATION_MESSAGE);

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 60 : 45;
        int maxQuestions = Math.min(10 + (difficulty * 5), wordPairs.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime || maxTries <= 0) {
                JOptionPane.showMessageDialog(frame, "Game over! You've used all your tries or time is up!", "GAME OVER",JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            String[] pair = wordPairs.removeFirst();
            String userAnswer = getUserInput(frame, "Translate: " + pair[0]);

            if (userAnswer == null) {
                if (confirmExit(frame)) {
                	frame.dispose();
                	return -1;
                }
                continue;
            }

            attempts++;
            if (userAnswer.equalsIgnoreCase(pair[1])) {
                JOptionPane.showMessageDialog(frame, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! Correct: " + pair[1]);
                maxTries--;
            }
        }

        frame.dispose();

        UserDatabase.updateStats(getUserID(), "WAPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }

    private String getUserInput(JFrame frame, String prompt) {
        return JOptionPane.showInputDialog(frame, prompt);
    }
    
    private boolean confirmExit (JFrame frame) {
    	int result = JOptionPane.showConfirmDialog(frame,"Do you want to exit the game?", "Exit Game",
    			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	return result == JOptionPane.YES_OPTION;
    }
}
