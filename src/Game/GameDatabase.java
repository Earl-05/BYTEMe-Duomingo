package Game;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.*;


public class GameDatabase {

    private static final String READING_FILE = getFilePath("reading.json");
    private static final String WORD_ASSOCIATION_FILE = getFilePath("word_association.json");
    private static final String WORD_GUESSING_FILE = getFilePath("word_guessing.json");
    private static final String USER_FILE = getFilePath("user.json");

    public static LinkedList<String[]> loadReadingData(String language, int difficulty) {
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
            Type type = new TypeToken<Map<String, Map<String, List<String[]>>>>() {}.getType();
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

    public static void updateGameStats(String userID, String gameType) {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            System.err.println("User file not found: " + USER_FILE);
            return;
        }
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> users = gson.fromJson(reader, type);

            boolean userFound = false;

            for (Map<String, Object> user : users) {
                if (user.get("userID").equals(userID)) {
                    userFound = true;
                    user.put("gamesPlayed", (double) user.get("gamesPlayed") + 1);

                    switch (gameType) {
                        case "WA":
                            user.put("WAPlayed", (double) user.get("WAPlayed") + 1);
                            break;
                        case "R":
                            user.put("RPlayed", (double) user.get("RPlayed") + 1);
                            break;
                        case "WG":
                            user.put("WGPlayed", (double) user.get("WGPlayed") + 1);
                            break;
                        default:
                            System.err.println("Invalid game type: " + gameType);
                            return;
                    }
                    break;
                }
            }

            if (!userFound) {
                System.err.println("User ID not found: " + userID);
                return;
            }

            try (Writer writer = new FileWriter(file)) {
                gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(users, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFilePath(String fileName) {
        String userDir = System.getProperty("user.dir");
        return userDir + File.separator + "src" + File.separator + "Game" + File.separator + fileName;
    }
}
