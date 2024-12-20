package Game;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class GameDatabase {

	private static final String READING_FILE = getFilePath("reading.json");
	private static final String WORD_ASSOCIATION_FILE = getFilePath("word_association.json");
	private static final String SCRAMBLE_WORD_FILE = getFilePath("scramble_word.json");
	private static final String USER_FILE = getFilePath("users.json");

    public static LinkedList<String[]> loadReadingData(String language, int difficulty) {
        return loadData(READING_FILE, language, difficulty, new LinkedList<>());
    }

    public static LinkedList<String[]> loadWordAssociationData(String language, int difficulty) {
        return loadData(WORD_ASSOCIATION_FILE, language, difficulty, new LinkedList<>());
    }

    public static List<ScrambleWordGame.WordEntry> loadScrambleWordData(String language, int difficulty) {
        return loadWordEntries(SCRAMBLE_WORD_FILE, language, difficulty);
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


    private static List<ScrambleWordGame.WordEntry> loadWordEntries(String fileName, String language, int difficulty) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File not found: " + fileName);
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Map<String, List<Map<String, String>>>>>() {}.getType();
            Map<String, Map<String, List<Map<String, String>>>> data = gson.fromJson(reader, type);

            String level = difficulty == 0 ? "Beginner" : "xIntermediate";

            Map<String, List<Map<String, String>>> languageData = data.get(language);
            if (languageData == null) {
                System.err.println("Language not found: " + language);
                return new ArrayList<>();
            }

            List<Map<String, String>> levelData = languageData.get(level);
            if (levelData == null) {
                System.err.println("Difficulty level not found: " + level);
                return new ArrayList<>();
            }

            List<ScrambleWordGame.WordEntry> wordEntries = new ArrayList<>();
            for (Map<String, String> wordMap : levelData) {
                String answer = wordMap.get("answer");
                String hint = wordMap.get("hint");
                if (answer != null && hint != null) {
                    wordEntries.add(new ScrambleWordGame.WordEntry(answer, hint));
                }
            }

            return wordEntries;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void updateGameStats(String userID, String gameType) {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            System.err.println("User file not found: " + USER_FILE);
            return;
        }
        
        List<Map<String, Object>> users = new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
            users = gson.fromJson(reader, type);
        } catch (IOException e) {
        	e.printStackTrace();
        	return;
        }

            boolean userFound = false;

            for (Map<String, Object> user : users) {
                if (user.get("userID").equals(userID)) {
                    userFound = true;
                    user.put("gamesPlayed", ((Number) user.get("gamesPlayed")).intValue() + 1);

                    switch (gameType) {
                        case "WA":
                        	user.put("WAPlayed", ((Number) user.get("WAPlayed")).intValue() + 1);
                            break;
                        case "R":
                        	user.put("RPlayed", ((Number) user.get("RPlayed")).intValue() + 1);
                            break;
                        case "SW":
                        	user.put("SWPlayed", ((Number) user.get("SWPlayed")).intValue() + 1);
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
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(users, writer);
            } catch (IOException e) {
            	e.printStackTrace();
        } 
          
    }

    private static String getFilePath(String fileName) {
        return System.getProperty("user.dir") + File.separator + "src" + File.separator + "Game" + File.separator + fileName;
    }
}