package gamesourc;

import java.util.Random;
import javax.swing.JOptionPane;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Random random = new Random();
        boolean playAgain;

        do {
            String prompt = "Welcome to Number Guessing Game! Wanna Play?";
            int choice = JOptionPane.showConfirmDialog(null, prompt, "Message Prompt", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Goodbye.");
                System.exit(0);
            }

            JOptionPane.showMessageDialog(null, "Let the games begin!");

            String[] options = {"Easy (1-10)", "Medium (1-50)", "Hard (1-100)"};
            int difficulty = JOptionPane.showOptionDialog(
                    null,
                    "Select a difficulty level:",
                    "Difficulty Level",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            int maxNumber = 10;
            int maxAttempts = 5;

            switch (difficulty) {
                case 0:
                    maxNumber = 10;
                    maxAttempts = 5;
                    break;
                case 1:
                    maxNumber = 50;
                    maxAttempts = 7;
                    break;
                case 2:
                    maxNumber = 100;
                    maxAttempts = 10;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice, defaulting to Easy (1-10).");
            }

            int targetNumber = random.nextInt(maxNumber) + 1;
            int guess = 0;
            int attempts = 0;
            boolean hasGuessedCorrectly = false;
            long startTime = System.currentTimeMillis();

            JOptionPane.showMessageDialog(null, "I have selected a number between 1 and " + maxNumber + ".");
            String input;

            while (attempts < maxAttempts) {
                input = JOptionPane.showInputDialog(null, "Enter your guess (Tries left: " + (maxAttempts - attempts) + "):");

                if (input == null) {
                    JOptionPane.showMessageDialog(null, "You cancelled the game. Goodbye!");
                    System.exit(0); 
                }

                if (input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                    continue; 
                }

                try {
                    guess = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                    continue; 
                }

                attempts++;

                if (guess < targetNumber) {
                    JOptionPane.showMessageDialog(null, "Too low! Guess a higher number.");
                } else if (guess > targetNumber) {
                    JOptionPane.showMessageDialog(null, "Too high! Guess a lower number.");
                } else {
                    long endTime = System.currentTimeMillis();
                    long timeTaken = (endTime - startTime) / 1000;
                    int score = calculateScore(attempts, timeTaken, maxNumber);

                    JOptionPane.showMessageDialog(null,
                        "Congratulations! You've guessed the correct number: " + targetNumber + "\n" +
                        "Attempts: " + attempts + "\n" +
                        "Time taken: " + timeTaken + " seconds\n" +
                        "Your score: " + score);

                    hasGuessedCorrectly = true;
                    break;
                }
            }

            if (!hasGuessedCorrectly) {
                long endTime = System.currentTimeMillis();
                long timeTaken = (endTime - startTime) / 1000;
                JOptionPane.showMessageDialog(null,
                    "Game over! The correct number was: " + targetNumber + "\n" +
                    "Attempts: " + attempts + "\n" +
                    "Time taken: " + timeTaken + " seconds\n" +
                    "Your score: 0");
            }

            int playAgainChoice = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
            playAgain = (playAgainChoice == JOptionPane.YES_OPTION);

        } while (playAgain);

        JOptionPane.showMessageDialog(null, "Thanks for playing! Goodbye.");
    }

    public static int calculateScore(int attempts, long timeTaken, int maxNumber) {
        int baseScore = maxNumber * 10;
        int score = baseScore - (attempts * 10 + (int) timeTaken * 5);
        return Math.max(score, 0);
    }
}
