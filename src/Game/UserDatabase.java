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

    public static boolean validateUser(String userName, String password) {
        for (UserDetails userDetails : userMap.values()) {
            if (userDetails.getUserName().equals(userName) && userDetails.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static UserDetails getUserByUsername(String userName) {
        for (UserDetails userDetails : userMap.values()) {
            if (userDetails.getUserName().equals(userName)) {
                return userDetails;
            }
        }
        return null;
    }

    public static void updateStats(String userID, String gameType, int score) {
        UserDetails user = userMap.get(userID);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "User not found. Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean won = score >= 50;

        switch (gameType) {
            case "WAPlayed":
                user.setWAPlayed(user.getWAPlayed() + 1);
                if (won) {
                    user.setWAWon(user.getWAWon() + 1);
                }
                break;
            case "SWPlayed":
                user.setSWPlayed(user.getSWPlayed() + 1);
                if (won) {
                    user.setSWWon(user.getSWWon() + 1);
                }
                break;
            case "RPlayed":
                user.setRPlayed(user.getRPlayed() + 1);
                if (won) {
                    user.setRWon(user.getRWon() + 1);
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid game type. Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        user.setGamesWon(user.getGamesWon() + 1);
        
        Achievement.checkAndUpdateAchievements(user);

        userMap.put(userID, user);
        saveDatabase();

        JOptionPane.showMessageDialog(null, "Stats updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int getUserAchievements(String userID) {
        UserDetails user = userMap.get(userID);
        if (user != null) {
            int totalAchievements = user.getWAWon() + user.getRWon() + user.getSWWon();
            JOptionPane.showMessageDialog(null, "User " + userID + " has achieved " + totalAchievements + " achievements.", "Achievements", JOptionPane.INFORMATION_MESSAGE);
            return totalAchievements;
        } else {
            JOptionPane.showMessageDialog(null, "User not found. Cannot fetch achievements.", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public static int getGlobalAchievements() {
        int totalAchievements = 0;
        for (UserDetails user : userMap.values()) {
            totalAchievements += user.getWAWon() + user.getSWWon() + user.getRWon();
        }
        JOptionPane.showMessageDialog(null, "Total achievements across all users: " + totalAchievements, "Global Achievements", JOptionPane.INFORMATION_MESSAGE);
        return totalAchievements;
    }

    private static String getDatabaseFilePath() {
        String userDir = System.getProperty("user.dir");
        return userDir + File.separator + "src" + File.separator + "Game" + File.separator + "users.json";
    }
}