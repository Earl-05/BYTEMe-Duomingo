package Game;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class User {

    static void welcomeUser(UserDetails userDetails) {
        JFrame frame = new JFrame("Welcome Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Welcome " + userDetails.getUserName() + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(60, 90, 150));
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(10));

        // Personal information label
        JLabel userInfo = new JLabel("<html>" +
                "Email: <b>" + userDetails.getEmail() + "</b><br>" +
                "Birthday: <b>" + userDetails.getBirthday() + "</b><br>" +
                "Current Course: <b>" + (userDetails.getCurrentCourse() == null ? "N/A" : userDetails.getCurrentCourse()) + "</b><br>" +
                "Main Language: <b>" + userDetails.getMainLanguage() + "</b>" +
                "</html>");
        userInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(userInfo);
        panel.add(Box.createVerticalStrut(10));

        // Option buttons
        String[] options = {"Course", "Achievements", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, panel, "Welcome Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Process the selected option
        switch (choice) {
            case 0:
                Course.displayCourse(userDetails);
                break;
            case 1:
                displayAchievements(userDetails);
                break;
            case 2:
                frame.dispose();  // Close the frame
                AppMain.MainScreen(new String[0]);  // Call the main screen method from AppMain
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
        }
    }

    private static void displayAchievements(UserDetails userDetails) {
        List<String> achievements = userDetails.getAchievements();

        if (achievements == null || achievements.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have no achievements yet.", "Achievements", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder achievementsList = new StringBuilder("Your Achievements:\n\n");
            for (String achievement : achievements) {
                if (achievement != null && !achievement.trim().isEmpty()) {
                    achievementsList.append("- ").append(achievement).append("\n");
                }
            }

            if (achievementsList.toString().equals("Your Achievements:\n\n")) {
                JOptionPane.showMessageDialog(null, "You have no valid achievements yet.", "Achievements", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, achievementsList.toString(), "Achievements", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
