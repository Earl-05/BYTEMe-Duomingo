package Game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class DataLoader {

    private static final String READING_FILE = "C:/Users/Ransss/Documents/GitHub/Finals/Finals/src/Game/reading.json";
    private static final String WORD_ASSOCIATION_FILE = "C:/Users/Ransss/Documents/GitHub/Finals/Finals/src/Game/word_association.json";
    private static final String WORD_GUESSING_FILE = "C:/Users/Ransss/Documents/GitHub/Finals/Finals/src/Game/word_guessing.json";

    public static Queue<String[]> loadReadingData(String language, int difficulty) {
        return loadData(READING_FILE, language, difficulty, new LinkedList<>());
    }

    public static LinkedList<String[]> loadWordAssociationData(String language, int difficulty) {
        return loadData(WORD_ASSOCIATION_FILE, language, difficulty, new LinkedList<>());
    }

    public static Stack<String[]> loadWordGuessingData(String language, int difficulty) {
        return loadData(WORD_GUESSING_FILE, language, difficulty, new Stack<>());
    }

    private static <T extends Collection<String[]>> T loadData(String fileName, String language, int difficulty, T collection) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File not found: " + fileName);
            return collection;
        }
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Map<String, List<String[]>>>>(){}.getType();
            Map<String, Map<String, List<String[]>>> data = gson.fromJson(reader, type);

            String level = (difficulty == 0) ? "Beginner" : "Intermediate";

            Map<String, List<String[]>> languageData = data.get(language);
            if (languageData == null) {
                System.err.println("Language not found: " + language);
                return collection;
            }

            List<String[]> levelData = languageData.get(level);
            if (levelData == null) {
                System.err.println("Difficulty level not found: " + level);
                return collection;
            }

            collection.addAll(levelData);
            return collection;
        } catch (IOException e) {
            e.printStackTrace();
            return collection;
        }
    }
}
