package Game;

import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class WordAssociation extends GameBase {
    private final HashMap<String, LinkedList<String[]>> languageData;

    public WordAssociation(int difficulty, String language) {
        super(difficulty, language);
        this.languageData = loadLanguageData();
    }

    @Override
    public int playGame() {
        LinkedList<String[]> wordPairs = languageData.getOrDefault(language, new LinkedList<>());
        if (wordPairs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available for the selected language.");
            return 0;
        }

        int attempts = 0;
        int score = 0;
        long startTime = System.currentTimeMillis();

        int maxQuestions = Math.min(10 + (difficulty * 5), wordPairs.size()); // More questions in higher difficulty
        for (int i = 0; i < maxQuestions; i++) {
            String[] pair = wordPairs.removeFirst();
            String userAnswer = JOptionPane.showInputDialog("What is the translation of: " + pair[0] + "?");
            attempts++;
            if (userAnswer != null && userAnswer.equalsIgnoreCase(pair[1])) {
                JOptionPane.showMessageDialog(null, "Correct!");
                score += 10;
            } else {
                JOptionPane.showMessageDialog(null, "Wrong! The correct answer is: " + pair[1]);
            }
        }

        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;
        int finalScore = GameMain.calculateScore(attempts, timeTaken, maxQuestions, difficulty);
        JOptionPane.showMessageDialog(null, "You scored " + finalScore + " points in Word Association.");
        return finalScore;
    }

    private HashMap<String, LinkedList<String[]>> loadLanguageData() {
        HashMap<String, LinkedList<String[]>> data = new HashMap<>();

        LinkedList<String[]> spanish = new LinkedList<>();
        spanish.add(new String[]{"Apple", "Manzana"});
        spanish.add(new String[]{"Dog", "Perro"});
        spanish.add(new String[]{"House", "Casa"});
        // Add more Spanish words
        data.put("Spanish", spanish);

        LinkedList<String[]> japanese = new LinkedList<>();
        japanese.add(new String[]{"Apple", "りんご"});
        japanese.add(new String[]{"Dog", "犬"});
        japanese.add(new String[]{"House", "家"});
        // Add more Japanese words
        data.put("Japanese", japanese);

        LinkedList<String[]> filipino = new LinkedList<>();
        filipino.add(new String[]{"Apple", "Mansanas"});
        filipino.add(new String[]{"Dog", "Aso"});
        filipino.add(new String[]{"House", "Bahay"});
        // Add more Filipino words
        data.put("Filipino", filipino);

        LinkedList<String[]> hiligaynon = new LinkedList<>();
        hiligaynon.add(new String[]{"Apple", "Mansanas"});
        hiligaynon.add(new String[]{"Dog", "Ido"});
        hiligaynon.add(new String[]{"House", "Balay"});
        // Add more Hiligaynon words
        data.put("Hiligaynon", hiligaynon);

        LinkedList<String[]> french = new LinkedList<>();
        french.add(new String[]{"Apple", "Pomme"});
        french.add(new String[]{"Dog", "Chien"});
        french.add(new String[]{"House", "Maison"});
        // Add more French words
        data.put("French", french);

        LinkedList<String[]> english = new LinkedList<>();
        english.add(new String[]{"Apple", "Apple"});
        english.add(new String[]{"Dog", "Dog"});
        english.add(new String[]{"House", "House"});
        // Add more English words
        data.put("English", english);

        return data;
    }
}
