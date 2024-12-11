package Game;
import javax.swing.JOptionPane;

public class GameMain {
    public static void main(String[] args) {
        // Language selection
        String[] languages = {"Spanish", "Japanese", "Filipino", "English", "French", "Hiligaynon"};
        String chosenLanguage = (String) JOptionPane.showInputDialog(null,
                "Choose a language course to learn:", "Language Selection",
                JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);

        if (chosenLanguage == null) {
            JOptionPane.showMessageDialog(null, "No language selected. Exiting the game.");
            return;
        }

        // Display game details
        String gameDetails = String.format("""
                Welcome to the Language Learning Game!
                
                Selected Language: %s
                
                Test your skills in:
                1. Word Association - Match words in your chosen language.
                2. Word Guessing - Guess the word based on a description.
                3. Reading - Translate sentences in your chosen language.
                
                Choose a difficulty level:
                - Beginner: Easy questions with more time and lenient scoring.
                - Intermediate: Tough questions with minimal time and high penalties.
                
                Start your linguistic adventure now!
                """, chosenLanguage);
        JOptionPane.showMessageDialog(null, gameDetails, "Game Details", JOptionPane.INFORMATION_MESSAGE);

        // Game selection
        String[] options = {"Word Association", "Word Guessing", "Reading", "Exit"};
        boolean keepPlaying = true;
        int totalScore = 0;

        while (keepPlaying) {
            int choice = JOptionPane.showOptionDialog(null, "Choose a game to play:", "Game Selection",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == -1 || choice == 3) {
                JOptionPane.showMessageDialog(null, "Thank you for playing! Your total score is: " + totalScore);
                keepPlaying = false;
            } else {
                int difficulty = chooseDifficulty();
                GameBase game = switch (choice) {
                    case 0 -> new WordAssociation(difficulty, chosenLanguage);
                    case 1 -> new WordGuessing(difficulty, chosenLanguage);
                    case 2 -> new Reading(difficulty, chosenLanguage);
                    default -> null;
                };

                if (game != null) {
                    totalScore += game.playGame();
                }
            }
        }
    }

    private static int chooseDifficulty() {
        String[] levels = {"Beginner", "Intermediate"};
        int difficulty = JOptionPane.showOptionDialog(null, "Select Difficulty Level:",
                "Difficulty Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, levels, levels[0]);
        return difficulty == -1 ? 0 : difficulty; // Default to Beginner if none selected
    }

    public static int calculateScore(int attempts, long timeTaken, int maxQuestions, int difficulty) {
        int baseScore = maxQuestions * (10 + (difficulty * 10)); // Points scale up with difficulty
        int penalty = (attempts * (5 + (difficulty * 5))) + ((int) timeTaken * (5 + (difficulty * 2)));
        int score = baseScore - penalty;
        return Math.max(score, 0);
    }
}
