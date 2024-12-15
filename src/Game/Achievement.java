package Game;

import javax.swing.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.*;

public class Achievement {

    private static String getUserFilePath() {
        return Paths.get(System.getProperty("user.dir"), "src", "Game", "users.json").toString();
    }

    public static void checkAndUpdateAchievements(UserDetails user) {
        List<String> achievements = user.getAchievements();

        // General Course Achievements
        if (!achievements.contains("First Steps") && user.getGamesWon() >= 1) {
            user.addAchievement("First Steps");
            JOptionPane.showMessageDialog(null, "First Steps! Completed your first game.", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!achievements.contains("Polyglot in Training") && user.getGamesWon() >= 10) {
            user.addAchievement("Polyglot in Training");
            JOptionPane.showMessageDialog(null, "Polyglot in Training! Completed 10 games.", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }

        // Word Association Achievements
        if (!achievements.contains("Matchmaker") && user.getWAWon() >= 1) {
            user.addAchievement("Matchmaker");
            JOptionPane.showMessageDialog(null, "Matchmaker! You won 1 Word Association game!", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!achievements.contains("Word Wizard") && user.getWAWon() >= 10) {
            user.addAchievement("Word Wizard");
            JOptionPane.showMessageDialog(null, "Word Wizard! You won 10 Word Association games!", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }

        // Reading Achievements
        if (!achievements.contains("Bookworm Beginner") && user.getRWon() >= 3) {
            user.addAchievement("Bookworm Beginner");
            JOptionPane.showMessageDialog(null, "Bookworm Beginner! You won 3 Reading games.", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!achievements.contains("Narrative Ninja") && user.getRWon() >= 10) {
            user.addAchievement("Narrative Ninja");
            JOptionPane.showMessageDialog(null, "Narrative Ninja! You won 10 Reading games!", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }

        // Scramble Word Game Achievements
        if (!achievements.contains("Scrambler") && user.getSWWon() >= 1) {
            user.addAchievement("Scrambler");
            JOptionPane.showMessageDialog(null, "Scrambler! You won 1 Scramble Word Game.", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }
        if (!achievements.contains("Word Scrambler Pro") && user.getSWWon() >= 5) {
            user.addAchievement("Word Scrambler Pro");
            JOptionPane.showMessageDialog(null, "Word Scrambler Pro! You won 5 Scramble Word Games.", "Achievement Unlocked", JOptionPane.INFORMATION_MESSAGE);
        }

        // Update the JSON file
        updateUserInFile(user);
    }

    private static void updateUserInFile(UserDetails updatedUser) {
        String userFilePath = getUserFilePath();
        try (FileReader reader = new FileReader(userFilePath)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<UserDetails>>() {}.getType();
            List<UserDetails> users = gson.fromJson(reader, userListType);

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserID().equals(updatedUser.getUserID())) {
                    users.set(i, updatedUser);
                    break;
                }
            }

            try (FileWriter writer = new FileWriter(userFilePath)) {
                Gson gsonWriter = new GsonBuilder().setPrettyPrinting().create();
                gsonWriter.toJson(users, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}