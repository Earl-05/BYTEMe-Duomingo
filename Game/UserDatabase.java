package Game;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import com.google.gson.*;

public class UserDatabase {
    private static final String DB_FILE = "C:/Users/Ransss/Documents/GitHub/Finals/Finals/src/Game/users.json";
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
}
