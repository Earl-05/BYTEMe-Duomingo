package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class ScrambleWordGame extends GameBase {
    private Stack<WordEntry> words;
    private int maxTime;
    private int maxTries;
    private List<String> specialCases;
    private UserDetails userDetails;

    public ScrambleWordGame(int difficulty, String language, UserDetails userDetails) {
        super(difficulty, language);
        this.userDetails = userDetails;
        List<WordEntry> wordList = GameDatabase.loadScrambleWordData(language, difficulty);
        Collections.shuffle(wordList);
        this.words = new Stack<>();
        this.words.addAll(wordList);
        this.maxTime = difficulty == 0 ? 120 : 75;
        this.maxTries = difficulty == 0 ? 5 : 3;
        this.specialCases = List.of(".", ",", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "?", ";", ":", "|");
    }

    @Override
    public int playGame() {
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        JFrame frame = new JFrame("Scramble Word Game");
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

        String instructionMessage = getInstructionMessage();
        JOptionPane.showMessageDialog(frame, instructionMessage, "Instruction", JOptionPane.INFORMATION_MESSAGE);

        int score = 0;
        long startTime = System.currentTimeMillis();
        boolean successfullyCompleted = true;
        while (!words.isEmpty()) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(frame, "Time's up! Game Over!", "Game Over", JOptionPane.ERROR_MESSAGE);
                successfullyCompleted = false;
                break;
            }

            if (maxTries <= 0) {
                JOptionPane.showMessageDialog(frame, "You've used all your tries! Game Over!", "Game Over", JOptionPane.ERROR_MESSAGE);
                successfullyCompleted = false;
                break;
            }

            WordEntry currentWord = words.pop();
            String userAnswer = getUserAnswer(frame, currentWord);

            if (userAnswer == null) {
                return score;
            }

            boolean containsSpecialCase = specialCases.stream().anyMatch(userAnswer::contains);
            if (containsSpecialCase) {
                JOptionPane.showMessageDialog(frame, "Special case detected. This input will be ignored.", "Special Case", JOptionPane.WARNING_MESSAGE);
                words.push(currentWord);
                continue;
            }

            if (userAnswer.equalsIgnoreCase(currentWord.getAnswer())) {
                JOptionPane.showMessageDialog(frame, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong! The correct answer was: " + currentWord.getAnswer());
               

                maxTries--;
            }
        }

        frame.dispose();
        showCompletionMessage(successfullyCompleted, score);

        updateUserStats(score);

        Achievement.checkAndUpdateAchievements(userDetails);

        return score;
    }

    private String getInstructionMessage() {
        return getDifficulty() == 0 ? "Welcome to the Scramble Word Game!\n"
                + "1. You'll be shown a scrambled word and a hint.\n"
                + "2. Unscramble the word to guess the correct answer.\n"
                + "3. You will have 5 tries and a time limit of 120 seconds.\n"
                + "Good luck!\n"
                + "Press 'Cancel' to exit the game and return to the main menu."
                : "Welcome to the Scramble Word Game!\n"
                + "1. You'll be shown a scrambled word and a hint.\n"
                + "2. Unscramble the word to guess the correct answer.\n"
                + "3. You will have 3 tries and a time limit of 75 seconds.\n"
                + "Good luck!\n"
                + "Press 'Cancel' to exit the game and return to the main menu.";
    }

    private String getUserAnswer(JFrame frame, WordEntry currentWord) {
        String userAnswer = null;

        do {
            userAnswer = JOptionPane.showInputDialog(frame,
                    "Scrambled Word: " + currentWord.getScrambled() + "\nHint: " + currentWord.getHint());

            if (userAnswer == null) {
                if (confirmExit(frame)) {
                    frame.dispose();
                    return null;
                }
            } else if (userAnswer.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Input cannot be empty. Please try again.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }

        } while (userAnswer == null || userAnswer.trim().isEmpty());

        return userAnswer.trim();
    }

    private void showCompletionMessage(boolean successfullyCompleted, int score) {
        if (successfullyCompleted) {
            JOptionPane.showMessageDialog(null, "Congratulations! You successfully completed the game. Your final score: " + score,
                    "Game Completed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Game over! Your final score: " + score,
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean confirmExit(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to exit the game?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    private void updateUserStats(int score) {
        String userID = userDetails.getUserID();

        userDetails.setSWPlayed(userDetails.getSWPlayed() + 1);
        if (score >= 50) { 
            userDetails.setSWWon(userDetails.getSWWon() + 1);
        }
        userDetails.setGamesPlayed(userDetails.getGamesPlayed() + 1);

        UserDatabase.updateUser(userDetails);
    }

    public static class WordEntry {
        private final String scrambled;
        private final String answer;
        private final String hint;

        public WordEntry(String answer, String hint) {
            this.answer = answer;
            this.hint = hint;
            this.scrambled = scrambleWord(answer);
        }

        public String getScrambled() {
            return scrambled;
        }

        public String getAnswer() {
            return answer;
        }

        public String getHint() {
            return hint;
        }

        public static String scrambleWord(String word) {
            List<Character> characters = new ArrayList<>();
            for (char c : word.toCharArray()) {
                characters.add(c);
            }
            Collections.shuffle(characters);
            StringBuilder scrambled = new StringBuilder();
            for (char c : characters) {
                scrambled.append(c);
            }
            return scrambled.toString();
        }
    }
}
