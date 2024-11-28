package Game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;


public class Reading extends GameBase {
    private final HashMap<String, Queue<String[]>> languageData;

    public Reading(int difficulty, String language) {
        super(difficulty, language);
        this.languageData = loadLanguageData();
    }

    @Override
    public int playGame() {
        Queue<String[]> sentences = languageData.getOrDefault(language, new LinkedList<>());
        if (sentences.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0;
        int score = 0;
        long startTime = System.currentTimeMillis();

        int maxQuestions = Math.min(10 + (difficulty * 5), sentences.size()); // More questions in higher difficulty
        for (int i = 0; i < maxQuestions; i++) {
            String[] sentencePair = sentences.poll();
            String foreignSentence = sentencePair[0];
            String correctAnswer = sentencePair[1];

            String userAnswer = JOptionPane.showInputDialog("Translate this sentence to English:\n" + foreignSentence);
            attempts++;
            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                JOptionPane.showMessageDialog(null, "Correct! The translation is: " + correctAnswer);
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! The correct translation is: " + correctAnswer);
            }
        }

        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        int finalScore = GameMain.calculateScore(attempts, timeTaken, maxQuestions, difficulty);
        JOptionPane.showMessageDialog(null, "You scored " + finalScore + " points in Reading.");
        return finalScore;
    }

    private HashMap<String, Queue<String[]>> loadLanguageData() {
        HashMap<String, Queue<String[]>> data = new HashMap<>();

        Queue<String[]> spanish = new LinkedList<>();
        spanish.add(new String[]{"La manzana es roja.", "The apple is red."});
        spanish.add(new String[]{"El perro es leal.", "The dog is loyal."});
        spanish.add(new String[]{"La casa es grande.", "The house is big."});
        // Add more Spanish sentences
        data.put("Spanish", spanish);

        Queue<String[]> japanese = new LinkedList<>();
        japanese.add(new String[]{"りんごは赤いです。", "The apple is red."});
        japanese.add(new String[]{"犬は忠実です。", "The dog is loyal."});
        japanese.add(new String[]{"家は大きいです。", "The house is big."});
        // Add more Japanese sentences
        data.put("Japanese", japanese);

        Queue<String[]> filipino = new LinkedList<>();
        filipino.add(new String[]{"Ang mansanas ay pula.", "The apple is red."});
        filipino.add(new String[]{"Ang aso ay tapat.", "The dog is loyal."});
        filipino.add(new String[]{"Ang bahay ay malaki.", "The house is big."});
        // Add more Filipino sentences
        data.put("Filipino", filipino);

        Queue<String[]> hiligaynon = new LinkedList<>();
        hiligaynon.add(new String[]{"Pula ang mansanas.", "The apple is red."});
        hiligaynon.add(new String[]{"Matinumanon ang ido.", "The dog is loyal."});
        hiligaynon.add(new String[]{"Dako ang balay.", "The house is big."});
        // Add more Hiligaynon sentences
        data.put("Hiligaynon", hiligaynon);

        Queue<String[]> french = new LinkedList<>();
        french.add(new String[]{"La pomme est rouge.", "The apple is red."});
        french.add(new String[]{"Le chien est loyal.", "The dog is loyal."});
        french.add(new String[]{"La maison est grande.", "The house is big."});
        // Add more French sentences
        data.put("French", french);

        Queue<String[]> english = new LinkedList<>();
        english.add(new String[]{"The apple is red.", "The apple is red."});
        english.add(new String[]{"The dog is loyal.", "The dog is loyal."});
        english.add(new String[]{"The house is big.", "The house is big."});
        // Add more English sentences
        data.put("English", english);

        return data;
    }
}
