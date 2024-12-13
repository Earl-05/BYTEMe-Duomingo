package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Reading extends GameBase {
    private Queue<String[]> sentences;
    private int maxTries;

    public Reading(int difficulty, String language) {
        super(difficulty, language);
        LinkedList<String[]> sentenceList = GameDatabase.loadReadingData(language, difficulty);
        Collections.shuffle(sentenceList); 
        this.sentences = new LinkedList<>(sentenceList);
        this.maxTries = difficulty == 0? 5 : 3;
    }

    @Override
    public int playGame() {
        if (sentences.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(confirmExit(frame)) {
                	frame.dispose();
                }
            }
        });

        JOptionPane.showMessageDialog(frame, "In this game, you will be given a sentence. Your task is to translate it correctly.\nYou will have 5 tries (Beginner) and 3 tries (Intermediate) and a time limit of 90 seconds (Beginner) and 60 seconds (Intermediate).\nPress 'Cancel' to exit the game and return to the main menu.","INSTRUCTION", JOptionPane.INFORMATION_MESSAGE);

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 90 : 60;
        int maxQuestions = Math.min(10 + (difficulty * 5), sentences.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime || maxTries <= 0) {
                JOptionPane.showMessageDialog(frame, "Game over! You've used all your tries or time is up!", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            String[] sentencePair = sentences.poll();
            String userAnswer = getUserInput("Translate: " + sentencePair[0]);

            if (userAnswer == null) {
                if(confirmExit(frame)) {
                	frame.dispose();
                	return -1;
                }
                continue;
            }

            attempts++;
            if (userAnswer.equalsIgnoreCase(sentencePair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! Correct: " + sentencePair[1]);
                maxTries--;
            }
        }

        UserDatabase.updateStats(getUserID(), "RPlayed");
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }
    
    private boolean confirmExit (JFrame frame) {
    	int result = JOptionPane.showConfirmDialog(frame,"Do you want to exit the game?", "Exit Game",
    			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	return result == JOptionPane.YES_OPTION;
    }
}