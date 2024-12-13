package Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class ScrambleWordGame extends GameBase {
    private Stack<WordEntry> words;
    private int maxTime;

    public ScrambleWordGame(int difficulty, String language) {
        super(difficulty, language);
        List<WordEntry> wordList = GameDatabase.loadScrambleWordData(language, difficulty);
        Collections.shuffle(wordList); // Shuffle the word list for randomness
        this.words = new Stack<>();
        this.words.addAll(wordList);
        this.maxTime = difficulty == 0 ? 120 : 75; // Time limit based on difficulty
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

        JOptionPane.showMessageDialog(frame,
                "Welcome to the Scramble Word Game!\n\n"
                        + "You'll be shown a scrambled word and a hint.\n"
                        + "Unscramble the word to guess the correct answer.\n"
                        + "Good luck!",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);

        int score = 0;
        long startTime = System.currentTimeMillis();

        while (!words.isEmpty()) {
            if ((System.currentTimeMillis() - startTime) / 1000 > maxTime) {
                JOptionPane.showMessageDialog(frame, "Time's up! Game Over!", "Game Over", JOptionPane.ERROR_MESSAGE);
                break;
            }

            WordEntry currentWord = words.pop();
            String userAnswer = JOptionPane.showInputDialog(frame,
                    "Scrambled Word: " + currentWord.getScrambled() + "\nHint: " + currentWord.getHint());

            if (userAnswer == null) {
                if (confirmExit(frame)) {
                    frame.dispose();
                    return score; // Save progress
                }
                continue;
            }

            if (userAnswer.equalsIgnoreCase(currentWord.getAnswer())) {
                JOptionPane.showMessageDialog(frame, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Wrong! The correct answer was: " + currentWord.getAnswer());
            }
        }

        frame.dispose();

        JOptionPane.showMessageDialog(null, "Your final score: " + score,
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        UserDatabase.updateStats(getUserID(), "SW");
        return score;
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

    // Nested WordEntry class for managing word data
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

        // Scramble the word directly in this method
        public static String scrambleWord(String word) {
            List<Character> characters = new ArrayList<>();
            for (char c : word.toCharArray()) {
                characters.add(c);
            }
            Collections.shuffle(characters); // Shuffle the characters to scramble the word
            StringBuilder scrambled = new StringBuilder();
            for (char c : characters) {
                scrambled.append(c);
            }
            return scrambled.toString();
        }
    }
}
