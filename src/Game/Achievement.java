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
        if (!achievements.contains("First Steps") && user.getGamesPlayed() >= 1) {
            achievements.add("First Steps");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: First Steps! Complete your first course.");
        }
        if (!achievements.contains("Polyglot in Training") && user.getGamesPlayed() >= 5) {
            achievements.add("Polyglot in Training");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Polyglot in Training! Complete 3 courses.");
        }

        // Word Association Achievements
        if (!achievements.contains("Matchmaker") && user.getWAPlayed() >= 1) {
            achievements.add("Matchmaker");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Matchmaker! You played 1 Word Association game!");
        }
        if (!achievements.contains("Word Wizard") && user.getWAPlayed() >= 10) {
            achievements.add("Word Wizard");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Word Wizard! You played 10 Word Association games!");
        }

        // Reading Achievements
        if (!achievements.contains("Bookworm Beginner") && user.getRPlayed() >= 3) {
            achievements.add("Bookworm Beginner");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Bookworm Beginner! You played 3 Reading games.");
        }
        if (!achievements.contains("Narrative Ninja") && user.getRPlayed() >= 10) {
            achievements.add("Narrative Ninja");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Narrative Ninja! You played 10 Reading games!");
        }

        // Scramble Word Game Achievements
        if (!achievements.contains("Scrambler") && user.getSWPlayed() >= 1) {
            achievements.add("Scrambler");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Scrambler! You played 1 Scramble Word Game.");
        }
        if (!achievements.contains("Word Scrambler Pro") && user.getSWPlayed() >= 5) {
            achievements.add("Word Scrambler Pro");
            JOptionPane.showMessageDialog(null, "Achievement Unlocked: Word Scrambler Pro! You played 5 Scramble Word Games.");
        }

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