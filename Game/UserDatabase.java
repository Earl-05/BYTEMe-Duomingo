package Game;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import com.google.gson.*;

public class UserDatabase {
    private static final String DB_FILE = "C:/Users/Ransss/Documents/GitHub/Finals/Finals/src/Game/users.json";
    private static Map<String, User> userMap = new HashMap<>();

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
            User[] users = gson.fromJson(reader, User[].class);
            if (users != null) {
                for (User user : users) {
                    userMap.put(user.getUserID(), user);
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

    public static void addUser(User user) {
        if (userMap.containsKey(user.getUserID())) {
            JOptionPane.showMessageDialog(null, "User ID already exists. Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            userMap.put(user.getUserID(), user);
            saveDatabase();
            JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static User getUser(String userID) {
        return userMap.get(userID);
    }

    public static void updateUser(User user) {
        if (userMap.containsKey(user.getUserID())) {
            userMap.put(user.getUserID(), user);
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

    public static List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    public static boolean validateUser(String userID, String password) {
        User user = userMap.get(userID);
        return user != null && user.getPassword().equals(password);
    }
}
