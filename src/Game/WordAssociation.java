package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WordAssociation extends GameBase {
    private LinkedList<String[]> wordPairs;
	private int maxTries;
	private List<String> specialCases;
	private boolean exitConfirmed = false;
	private UserDetails userDetails;

    public WordAssociation(int difficulty, String language, UserDetails userDetails) {
        super(difficulty, language);
        this.wordPairs = GameDatabase.loadWordAssociationData(language, difficulty);
        Collections.shuffle(wordPairs);
        this.maxTries = difficulty == 0? 5 : 3;
        this.specialCases = List.of(".", ",", "!", "@", "#", "$" , "%" , "^" , "&" , "*" , "(" , ")" , "-" , "_" , "=" , "+" , "?" , ";" , ":" , "|");
        this.userDetails = userDetails;
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
        
        String instructionMessage;
        if (difficulty == 0) {
            instructionMessage = "Welcome to the Word Association Game!\n"
                + "1. In this game, you will be given a word.\n"
                + "2. Your task is to translate it correctly.\n"
                + "3. You will have 5 tries and a time limit of 120 seconds.\n"
                + "Good Luck!\n"
                + "Press 'Cancel' to exit the game and return to the main menu.";
        } else {
            instructionMessage = "Welcome to the Word Association Game!\n"
                + "1. In this game, you will be given a word.\n"
                + "2. Your task is to translate it correctly.\n"
                + "3. You will have 3 tries and a time limit of 60 seconds.\n"
                + "Good Luck!\n"
                + "Press 'Cancel' to exit the game and return to the main menu.";
        }

        JOptionPane.showMessageDialog(frame, instructionMessage, "Instruction", JOptionPane.INFORMATION_MESSAGE);

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = difficulty == 0 ? 120 : 45;
        int maxQuestions = Math.min(10 + (difficulty * 5), wordPairs.size());

        for (int i = 0; i < maxQuestions; i++) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(frame, "Time is up! Game Over!", "Game Over",JOptionPane.ERROR_MESSAGE);
                break;
            }
            
            if (maxTries <= 0) {
            	JOptionPane.showMessageDialog(frame, "You've used all of your tries! Game Over!", "Game Over", JOptionPane.ERROR_MESSAGE);
            	break;
            }

            String[] pair = wordPairs.removeFirst();
            String userAnswer = getUserInput(frame, "Translate: " + pair[0]);

            if (userAnswer == null) {
                if (confirmExit(frame)) {
                	frame.dispose();
                	return -1;
                }
                
                i--;
                
                continue;
            }
            
            if (specialCases.contains(userAnswer.trim().toLowerCase())) {
            	JOptionPane.showMessageDialog(frame, "Special case detected. This input will be ignored.", "Special Case", JOptionPane.WARNING_MESSAGE);
            	i--;
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
            
            if (maxTries == 0) {
            	JOptionPane.showMessageDialog(frame, "You've used all of your tries! Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            	break;
            }
        }

        frame.dispose();
        
        String userID = userDetails.getUserID();
        if (userID == null) {
        	JOptionPane.showMessageDialog(null, "User not logged in. Cannot update stats.", "Error", JOptionPane.ERROR_MESSAGE);
        	return 0;
        }
        
        if (maxTries > 0) {
        	JOptionPane.showMessageDialog(null, "Congratulations! Your final score: " + score, "Game Completed", JOptionPane.INFORMATION_MESSAGE);
        	UserDatabase.updateStats(userID.trim(), "WAPlayed", score);
        } else {
        	JOptionPane.showMessageDialog(null, "Game Over! Your final score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, maxQuestions);
    }

    private String getUserInput(JFrame frame, String prompt) {
    	String input = null;
    	boolean containsSpecialCase = false;
    	
    	while (input == null || input.trim().isEmpty() || containsSpecialCase) {
    		containsSpecialCase = false;
    		input = JOptionPane.showInputDialog(frame, prompt);
    		if (input == null) {
    			if (confirmExit(frame)) {
    				if (frame.isDisplayable()) {
    				frame.dispose();
    				}
    				return null;	
    			}
    		}
    		
    		input = input.trim();
    		if (input.isEmpty()) {
    			JOptionPane.showMessageDialog(frame, "The input is empty. Please input an answer and try again.", "Invalid", JOptionPane.WARNING_MESSAGE);
    		} else {
    			for (String special : specialCases) {
    				if (input.contains(special)) {
    					containsSpecialCase = true;
    					JOptionPane.showMessageDialog(frame, "Special case detected. The input will be ignored.", "Special Case", JOptionPane.WARNING_MESSAGE);
    					break;
    				}
    			}
    		}
    	}
        return input;
    }
    
    private boolean confirmExit (JFrame frame) {
    	if (exitConfirmed) {
    		return true;
    	}
    	int result = JOptionPane.showConfirmDialog(frame,"Do you want to exit the game?", "Exit Game",
    			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	exitConfirmed = (result == JOptionPane.YES_OPTION);
    	return exitConfirmed;
    }
}
