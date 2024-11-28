package Game;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JOptionPane;

public class WordGuessing extends GameBase {
    private final HashMap<String, Stack<String[]>> languageData;

    public WordGuessing(int difficulty, String language) {
        super(difficulty, language);
        this.languageData = loadLanguageData();
    }

    @Override
    public int playGame() {
        Stack<String[]> words = languageData.getOrDefault(language, new Stack<>());
        if (words.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0;
        int score = 0;
        long startTime = System.currentTimeMillis();

        int maxQuestions = Math.min(10 + (difficulty * 5), words.size()); // More questions in higher difficulty
        for (int i = 0; i < maxQuestions; i++) {
            String[] wordPair = words.pop();
            String description = wordPair[0];
            String correctAnswer = wordPair[1];

            String userAnswer = JOptionPane.showInputDialog("Guess the word in " + language + ":\n" + description);
            attempts++;
            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                JOptionPane.showMessageDialog(null, "Correct! The word is: " + correctAnswer);
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! The correct answer is: " + correctAnswer);
            }
        }

        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        int finalScore = GameMain.calculateScore(attempts, timeTaken, maxQuestions, difficulty);
        JOptionPane.showMessageDialog(null, "You scored " + finalScore + " points in Word Guessing.");
        return finalScore;
    }

    private HashMap<String, Stack<String[]>> loadLanguageData() {
        HashMap<String, Stack<String[]>> data = new HashMap<>();

        Stack<String[]> spanish = new Stack<>();
        spanish.push(new String[]{"A sweet fruit, red or green.", "Manzana"});
        spanish.push(new String[]{"A loyal animal kept as a pet.", "Perro"});
        spanish.push(new String[]{"A place where you live.", "Casa"});
        // Add more Spanish descriptions
        data.put("Spanish", spanish);

        Stack<String[]> japanese = new Stack<>();
        japanese.push(new String[]{"A sweet fruit, red or green.", "りんご"});
        japanese.push(new String[]{"A loyal animal kept as a pet.", "犬"});
        japanese.push(new String[]{"A place where you live.", "家"});
        // Add more Japanese descriptions
        data.put("Japanese", japanese);

        Stack<String[]> filipino = new Stack<>();
        filipino.push(new String[]{"A sweet fruit, red or green.", "Mansanas"});
        filipino.push(new String[]{"A loyal animal kept as a pet.", "Aso"});
        filipino.push(new String[]{"A place where you live.", "Bahay"});
        // Add more Filipino descriptions
        data.put("Filipino", filipino);

        Stack<String[]> hiligaynon = new Stack<>();
        hiligaynon.push(new String[]{"A sweet fruit, red or green.", "Mansanas"});
        hiligaynon.push(new String[]{"A loyal animal kept as a pet.", "Ido"});
        hiligaynon.push(new String[]{"A place where you live.", "Balay"});
        // Add more Hiligaynon descriptions
        data.put("Hiligaynon", hiligaynon);

        Stack<String[]> french = new Stack<>();
        french.push(new String[]{"A sweet fruit, red or green.", "Pomme"});
        french.push(new String[]{"A loyal animal kept as a pet.", "Chien"});
        french.push(new String[]{"A place where you live.", "Maison"});
        // Add more French descriptions
        data.put("French", french);

        Stack<String[]> english = new Stack<>();
        english.push(new String[]{"A sweet fruit, red or green.", "Apple"});
        english.push(new String[]{"A loyal animal kept as a pet.", "Dog"});
        english.push(new String[]{"A place where you live.", "House"});
        // Add more English descriptions
        data.put("English", english);

        return data;
    }
}
