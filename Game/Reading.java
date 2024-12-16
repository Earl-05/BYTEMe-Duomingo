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

        // Display instructions
        displayInstructions();

        int attempts = 0, score = 0;
        long startTime = System.currentTimeMillis();
        int maxTime = getDifficulty() == 0 ? 90 : 60;

        while (!questions.isEmpty() && maxTries > 0) {
            if (isTimeUp(startTime, maxTime)) {
                JOptionPane.showMessageDialog(frame, "Time's up! Game over!", "Game Over", JOptionPane.ERROR_MESSAGE);
                break;
            }

            String[] questionSet = questions.poll();
            if (askQuestion(frame, questionSet)) {
                score += 10;
            } else {
                maxTries--;
            }

            attempts++;
        }

        JOptionPane.showMessageDialog(null, "Your final score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Update stats and check achievements
        String userID = getUserID();
        UserDatabase.updateStats(userID, "RPlayed", score);
        UserDetails user = UserDatabase.getUser(userID);
        if (user != null) {
            Achievement.checkAndUpdateAchievements(user);
            UserDatabase.saveDatabase(); // Ensure changes are persisted
        }

        return calculateScore(attempts, (System.currentTimeMillis() - startTime) / 1000, attempts);
    }

    private void displayInstructions() {
        String instructionMessage = getDifficulty() == 0
                ? "Welcome to the Reading Game!\nYou have 5 tries and 90 seconds to complete the game."
                : "Welcome to the Reading Game!\nYou have 3 tries and 60 seconds to complete the game.";
        JOptionPane.showMessageDialog(null, instructionMessage, "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isTimeUp(long startTime, int maxTime) {
        return (System.currentTimeMillis() - startTime) / 1000 > maxTime;
    }

    private boolean askQuestion(JFrame frame, String[] questionSet) {
        String questionPrompt = "Context Clue: " + questionSet[0] + "\n\n" + questionSet[1];
        String[] options = {questionSet[3], questionSet[2], questionSet[4]};
        shuffleArray(options);

        String userAnswer = (String) JOptionPane.showInputDialog(frame, questionPrompt, "Choose the Answer",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (userAnswer == null) {
            if (confirmExit(frame)) {
                frame.dispose();
                return false;
            }
            return false;
        }

        if (userAnswer.equals(questionSet[2])) {
            JOptionPane.showMessageDialog(frame, "Correct! Well done.");
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "Wrong! The correct answer was: " + questionSet[2]);
            return false;
        }
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
