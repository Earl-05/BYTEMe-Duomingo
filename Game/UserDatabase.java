package Game;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserDatabase {
    private static final String DB_FILE = getDatabaseFilePath();
    private static Map<String, UserDetails> userMap = new HashMap<>();

    static {
        loadDatabase();
    }

    public static void loadDatabase() {
        File file = new File(DB_FILE);
        if (!file.exists()) {
            System.out.println("Database file not found at: " + file.getAbsolutePath());
            saveDatabase();
            return;
        }
        try (Reader reader = new FileReader(DB_FILE)) {
            Gson gson = new Gson();
            UserDetails[] users = gson.fromJson(reader, UserDetails[].class);
            if (users != null) {
                for (UserDetails userDetails : users) {
                    userMap.put(userDetails.getUserID(), userDetails);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the database: " + e.getMessage());
        }
    }

    public static void saveDatabase() {
        File file = new File(DB_FILE);
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userMap.values(), writer);
        } catch (IOException e) {
            System.out.println("Error saving the database: " + e.getMessage());
        }
    }

    public static void addUser(UserDetails userDetails) {
        if (userMap.containsKey(userDetails.getUserID())) {
            JOptionPane.showMessageDialog(null, "User ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userMap.put(userDetails.getUserID(), userDetails);
            saveDatabase();
            JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static UserDetails getUser(String userID) {
        return userMap.get(userID);
    }

    public static void updateUser(UserDetails userDetails) {
        if (userMap.containsKey(userDetails.getUserID())) {
            userMap.put(userDetails.getUserID(), userDetails);
            saveDatabase();
            JOptionPane.showMessageDialog(null, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "User not found. Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteUser(String userID) {
        if (userMap.remove(userID) != null) {
            saveDatabase();
            JOptionPane.showMessageDialog(null, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "User not found. Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<UserDetails> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    public static boolean validateUser(String userID, String password) {
        UserDetails userDetails = userMap.get(userID);
        return userDetails != null && userDetails.getPassword().equals(password);
    }
    
    public static void updateStats(String userID, String gameType) {
        UserDetails user = userMap.get(userID);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "User not found. Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (gameType) {
            case "WAPlayed":
                user.setWAPlayed(user.getWAPlayed() + 1);
                break;
            case "RPlayed":
                user.setRPlayed(user.getRPlayed() + 1);
                break;
            case "WGPlayed":
                user.setWGPlayed(user.getWGPlayed() + 1);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid game type. Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        // Update the total games played
        user.setGamesPlayed(user.getGamesPlayed() + 1);

        // Save the updated user data back to the database
        userMap.put(userID, user); // Update the map
        saveDatabase(); // Save changes to the JSON file

        JOptionPane.showMessageDialog(null, "Stats updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }


    private static String getDatabaseFilePath() {
        String userDir = System.getProperty("user.dir");
        return userDir + File.separator + "src" + File.separator + "Game" + File.separator + "users.json";
    }
    
    
}
