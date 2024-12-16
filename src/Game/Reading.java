package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Reading extends GameBase {
    private Queue<String[]> questions; 
    private int maxTries;

    public Reading(int difficulty, String language) {
        super(difficulty, language);
        LinkedList<String[]> questionList = GameDatabase.loadReadingData(language, difficulty);
        Collections.shuffle(questionList); 
        this.questions = new LinkedList<>(questionList);
        this.maxTries = difficulty == 0 ? 5 : 3;
    }

    @Override
    public int playGame() {
        if (questions.isEmpty()) {
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
        
        String instructionMessage;

        if (difficulty == 0) {
            instructionMessage = "Welcome to the Reading Game!\n"
                + "1. You will be presented with a sentence and a question based on context clues.\n"
                + "2. Choose the correct answer from the multiple-choice options.\n"
                + "3. You will have 5 tries and a time limit of 90 seconds.\n"
                + "Good Luck!\n"
                + "Press 'Cancel' to exit the game and return to the main menu.";
        } else {
            instructionMessage = "Welcome to the Reading Game!\n"
                + "1. You will be presented with a sentence and a question based on context clues.\n"
                + "2. Choose the correct answer from the multiple-choice options.\n"
                + "3. You will have 3 tries and a time limit of 60 seconds.\n"
                + "Good Luck!\n"
                + "Press 'Cancel' to exit the game and return to the main menu.";
        }


        JOptionPane.showMessageDialog(frame, instructionMessage, "Instruction", JOptionPane.INFORMATION_MESSAGE);

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 90 : 60;
        int maxQuestions = questions.size();

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(frame, "Time's up! Game over!", "Game Over", JOptionPane.ERROR_MESSAGE);
                break;
            }
           
            String[] questionSet = questions.poll(); 
            if (questionSet == null) {
            	break;
            }
            
            String questionPrompt = "Context Clue: " + questionSet[0] + "\n\n" + questionSet[1];
            String[] options = {questionSet[3], questionSet[2], questionSet[4]};
            shuffleArray(options); 
            String userAnswer = (String) JOptionPane.showInputDialog(frame, questionPrompt, "Choose the Answer",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (userAnswer == null) {
                if (confirmExit(frame)) {
                    frame.dispose();
                    return -1;
                }
                continue;
            }

            attempts++;
            if (userAnswer.equals(questionSet[2])) { 
                JOptionPane.showMessageDialog(frame, "Correct! Well done.");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! The correct answer was: " + questionSet[2]);
                maxTries--;
                
                if (maxTries == 0) {
                	JOptionPane.showMessageDialog(frame, "You've used up all of your tries. Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                	break;
                }
            }
            
        }
        
        if (maxTries <= 0) {
        	JOptionPane.showMessageDialog(null, "Your final score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
        	JOptionPane.showMessageDialog(null, "Congratulations! Your final score: " + score, "Game Completed", JOptionPane.INFORMATION_MESSAGE);
        }
        UserDatabase.updateStats(getUserID(), "RPlayed", score);
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, attempts);
    }

    private boolean confirmExit(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to exit the game?", "Exit Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    private void shuffleArray(String[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}